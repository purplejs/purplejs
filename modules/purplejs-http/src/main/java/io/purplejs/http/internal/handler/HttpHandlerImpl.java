package io.purplejs.http.internal.handler;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.io.ByteSource;

import io.purplejs.core.Engine;
import io.purplejs.core.exception.ProblemException;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.internal.error.ErrorRenderer;
import io.purplejs.http.internal.websocket.WebSocketRegistry;
import io.purplejs.http.websocket.WebSocketEvent;
import io.purplejs.http.websocket.WebSocketEventType;
import io.purplejs.http.websocket.WebSocketSession;

final class HttpHandlerImpl
    implements HttpHandler
{
    private final static Logger LOG = Logger.getLogger( HttpHandlerImpl.class.getName() );

    Engine engine;

    ResourcePath resource;

    ErrorRenderer errorRenderer;

    WebSocketRegistry webSocketRegistry;

    @Override
    public Response serve( final Request request )
    {
        final ServeRequestCommand command = new ServeRequestCommand( request );
        return execute( command );
    }

    private <R> R execute( final Function<ScriptExports, R> command )
    {
        final ScriptExports exports = this.engine.require( this.resource );
        return command.apply( exports );
    }

    @Override
    public Response errorIfNeeded( final Request request, final Response response )
    {
        if ( request.isWebSocket() )
        {
            return response;
        }

        final Status status = response.getStatus();
        final boolean isError = status.isServerError() || status.isClientError();

        if ( !isError )
        {
            return response;
        }

        final ByteSource body = response.getBody();
        if ( body != null )
        {
            return response;
        }

        return handleError( request, status, null );
    }

    @Override
    public Response handleException( final Request request, final Throwable cause )
    {
        LOG.log( Level.SEVERE, "Request [" + request.getUri() + "] caused an exception", cause );
        if ( request.isWebSocket() )
        {
            return null;
        }

        return handleError( request, Status.INTERNAL_SERVER_ERROR, cause );
    }

    private Response handleError( final Request request, final Status status, final Throwable cause )
    {
        try
        {
            final Response result = handleErrorInJs( request, status, cause );
            if ( result != null )
            {
                return result;
            }
        }
        catch ( final Exception e )
        {
            // Do nothing
        }

        return this.errorRenderer.handle( request, status, cause );
    }

    private Response handleErrorInJs( final Request request, final Status status, final Throwable cause )
    {
        if ( !canHandleErrorInJs( cause ) )
        {
            return null;
        }

        final ServeErrorCommand command = new ServeErrorCommand( request, status, cause );
        return execute( command );
    }

    private boolean canHandleErrorInJs( final Throwable cause )
    {
        return !( cause instanceof ProblemException );
    }

    @Override
    public boolean handleEvent( final WebSocketEvent event )
    {
        final WebSocketSession session = event.getSession();
        final WebSocketEventType type = event.getType();

        if ( type == WebSocketEventType.OPEN )
        {
            this.webSocketRegistry.add( session );
        }
        else if ( type == WebSocketEventType.CLOSE )
        {
            this.webSocketRegistry.remove( session );
        }

        final HandleEventCommand command = new HandleEventCommand( event );
        return execute( command );
    }
}
