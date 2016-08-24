package io.purplejs.http.internal;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineModule;
import io.purplejs.http.Request;
import io.purplejs.http.error.ErrorHandler;
import io.purplejs.http.handler.HttpHandlerFactory;
import io.purplejs.http.internal.error.DefaultErrorHandler;
import io.purplejs.http.internal.handler.HttpHandlerFactoryImpl;
import io.purplejs.http.internal.websocket.WebSocketRegistry;
import io.purplejs.http.internal.websocket.WebSocketRegistryImpl;
import io.purplejs.http.websocket.WebSocketManager;

public final class HttpModule
    implements EngineModule
{
    private HttpHandlerFactoryImpl handlerFactory;

    @Override
    public void configure( final EngineBinder binder )
    {
        this.handlerFactory = new HttpHandlerFactoryImpl();

        binder.provider( Request.class, new RequestAccessor() );
        binder.instance( ErrorHandler.class, new DefaultErrorHandler() );
        binder.instance( HttpHandlerFactory.class, this.handlerFactory );

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
