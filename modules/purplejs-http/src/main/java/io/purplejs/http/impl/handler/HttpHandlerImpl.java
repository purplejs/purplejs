package io.purplejs.http.impl.handler;

import java.util.function.Function;

import com.google.common.io.ByteSource;

import io.purplejs.Engine;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.impl.error.ExceptionRenderer;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

final class HttpHandlerImpl
    implements HttpHandler
{
    Engine engine;

    ResourcePath resource;

    ExceptionRenderer exceptionRenderer;

    @Override
    public Response serve( final Request request )
    {
        try
        {
            final ServeRequestCommand command = new ServeRequestCommand( request );
            final Response response = execute( command );
            return errorIfNeeded( request, response );
        }
        catch ( final Exception e )
        {
            return this.exceptionRenderer.handle( request, e );
        }
    }

    private <R> R execute( final Function<ScriptExports, R> command )
    {
        final ScriptExports exports = this.engine.require( this.resource );
        return command.apply( exports );
    }

    private Response errorIfNeeded( final Request request, final Response response )
        throws Exception
    {
        final Status status = response.getStatus();
        final boolean isError = status.isServerError() || status.isClientError();

        if ( !isError )
        {
            return response;
        }

        final ByteSource body = response.getBody();
        if ( !body.isEmpty() )
        {
            return response;
        }

        return this.exceptionRenderer.handle( request, status );
    }
}
