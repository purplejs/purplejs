package io.purplejs.http.internal.handler;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteSource;

import io.purplejs.core.Engine;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.internal.error.ErrorRenderer;
import io.purplejs.http.internal.websocket.WebSocketRegistry;
import io.purplejs.http.websocket.WebSocketEvent;
import io.purplejs.http.websocket.WebSocketSession;

final class HttpHandlerImpl
    implements HttpHandler
{
    private final static Logger LOG = LoggerFactory.getLogger( HttpHandlerImpl.class );

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

        return this.errorRenderer.handle( request, status );
    }

    @Override
    public Response handleException( final Request request, final Throwable cause )
    {
        LOG.error( "Request [" + request.getUri() + "] caused an exception", cause );
        if ( request.isWebSocket() )
        {
            return null;
        }

        return this.errorRenderer.handle( request, cause );
    }

    @Override
    public boolean handleEvent( final WebSocketEvent event )
    {
        final WebSocketSession session = event.getSession();
        switch ( event.getType() )
        {
            case OPEN:
                this.webSocketRegistry.add( session );
                break;
            case CLOSE:
                this.webSocketRegistry.remove( session );
                break;
        }

        final HandleEventCommand command = new HandleEventCommand( event );
        return execute( command );
    }
}
