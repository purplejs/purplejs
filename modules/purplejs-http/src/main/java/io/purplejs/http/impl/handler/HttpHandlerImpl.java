package io.purplejs.http.impl.handler;

import java.util.function.Function;

import io.purplejs.Engine;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

final class HttpHandlerImpl
    implements HttpHandler
{
    Engine engine;

    ResourcePath resource;

    ExceptionHandler exceptionHandler;

    void init()
    {
        if ( !this.engine.isDevMode() )
        {
            this.engine.require( this.resource );
        }
    }

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
}
