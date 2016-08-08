package io.purplejs.http.impl.handler;

import java.util.function.Function;

import io.purplejs.Engine;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
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
            return execute( command );
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
}
