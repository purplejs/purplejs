package io.purplejs.http.internal.handler;

import io.purplejs.core.Engine;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.http.error.ErrorHandler;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.handler.HttpHandlerFactory;
import io.purplejs.http.internal.error.ErrorRendererImpl;
import io.purplejs.http.internal.websocket.WebSocketRegistry;

public final class HttpHandlerFactoryImpl
    implements HttpHandlerFactory
{
    private Engine engine;

    private ErrorHandler errorHandler;

    private WebSocketRegistry webSocketRegistry;

    public void init( final Engine engine )
    {
        this.engine = engine;
        this.errorHandler = this.engine.getInstance( ErrorHandler.class );
        this.webSocketRegistry = this.engine.getInstance( WebSocketRegistry.class );
    }

    @Override
    public HttpHandler newHandler( final ResourcePath resource )
    {
        final HttpHandlerImpl handler = new HttpHandlerImpl();
        handler.resource = resource;
        handler.engine = this.engine;
        handler.errorRenderer = new ErrorRendererImpl( this.errorHandler, this.engine.getResourceLoader() );
        handler.webSocketRegistry = this.webSocketRegistry;
        return handler;
    }
}
