package io.purplejs.http.impl.handler;

import io.purplejs.Engine;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.handler.HttpHandlerFactory;
import io.purplejs.resource.ResourcePath;

public final class HttpHandlerFactoryImpl
    implements HttpHandlerFactory
{
    private Engine engine;

    private ExceptionHandler exceptionHandler;

    public void init( final Engine engine )
    {
        this.engine = engine;
        this.exceptionHandler = this.engine.getInstance( ExceptionHandler.class );
    }

    @Override
    public HttpHandler newHandler( final ResourcePath resource )
    {
        final HttpHandlerImpl handler = new HttpHandlerImpl();
        handler.resource = resource;
        handler.engine = this.engine;
        handler.exceptionHandler = this.exceptionHandler;

        handler.init();
        return handler;
    }
}
