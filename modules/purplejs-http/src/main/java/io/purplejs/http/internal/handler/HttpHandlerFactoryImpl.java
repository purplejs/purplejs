package io.purplejs.http.internal.handler;

import io.purplejs.core.Engine;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.handler.HttpHandlerFactory;
import io.purplejs.http.internal.error.ExceptionRenderer;
import io.purplejs.core.resource.ResourcePath;

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
        handler.exceptionRenderer = new ExceptionRenderer( this.exceptionHandler, this.engine.getResourceLoader() );
        return handler;
    }
}
