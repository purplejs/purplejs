package io.purplejs.http.internal.handler;

import io.purplejs.core.Engine;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.http.error.ErrorHandler;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.handler.HttpHandlerFactory;
import io.purplejs.http.internal.error.ErrorRendererImpl;

public final class HttpHandlerFactoryImpl
    implements HttpHandlerFactory
{
    private Engine engine;

    private ErrorHandler errorHandler;

    public void init( final Engine engine )
    {
        this.engine = engine;
        this.errorHandler = this.engine.getInstance( ErrorHandler.class );
    }

    @Override
    public HttpHandler newHandler( final ResourcePath resource )
    {
        final HttpHandlerImpl handler = new HttpHandlerImpl();
        handler.resource = resource;
        handler.engine = this.engine;
        handler.errorRenderer = new ErrorRendererImpl( this.errorHandler, this.engine.getResourceLoader() );
        return handler;
    }
}
