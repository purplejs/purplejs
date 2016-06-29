package io.purplejs.http.impl;

import io.purplejs.Engine;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.executor.HttpHandler;
import io.purplejs.http.impl.command.ServeRequestCommand;
import io.purplejs.resource.ResourcePath;

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
        return this.engine.execute( this.resource, new ServeRequestCommand( request ) );
    }
}
