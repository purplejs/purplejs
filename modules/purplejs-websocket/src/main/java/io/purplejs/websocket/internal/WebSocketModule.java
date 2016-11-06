package io.purplejs.websocket.internal;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineModule;
import io.purplejs.http.internal.websocket.WebSocketRegistry;
import io.purplejs.http.internal.websocket.WebSocketRegistryImpl;
import io.purplejs.http.websocket.WebSocketManager;
import io.purplejs.websocket.handler.WebSocketHandlerFactory;

public final class WebSocketModule
    implements EngineModule
{
    private WebSocketHandlerFactoryImpl handlerFactory;

    @Override
    public void configure( final EngineBinder binder )
    {
        this.handlerFactory = new WebSocketHandlerFactoryImpl();
        binder.instance( WebSocketHandlerFactory.class, this.handlerFactory );

        final WebSocketRegistry webSocketRegistry = new WebSocketRegistryImpl();
        binder.instance( WebSocketRegistry.class, webSocketRegistry );
        binder.instance( WebSocketManager.class, webSocketRegistry );

        binder.initializer( this::initialize );
    }

    private void initialize( final Engine engine )
    {
        this.handlerFactory.init( engine );
    }
}
