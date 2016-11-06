package io.purplejs.websocket.internal;

import io.purplejs.core.Engine;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.http.internal.websocket.WebSocketRegistry;
import io.purplejs.websocket.handler.WebSocketHandler;
import io.purplejs.websocket.handler.WebSocketHandlerFactory;

final class WebSocketHandlerFactoryImpl
    implements WebSocketHandlerFactory
{
    private Engine engine;

    private WebSocketRegistry registry;

    void init( final Engine engine )
    {
        this.engine = engine;
        this.registry = this.engine.getInstance( WebSocketRegistry.class );
    }

    @Override
    public WebSocketHandler newHandler( final ResourcePath resource )
    {
        final WebSocketHandlerImpl handler = new WebSocketHandlerImpl();
        handler.resource = resource;
        handler.engine = this.engine;
        handler.registry = this.registry;
        return handler;
    }
}
