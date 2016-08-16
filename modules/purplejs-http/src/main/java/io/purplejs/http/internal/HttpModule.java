package io.purplejs.http.internal;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineModule;
import io.purplejs.http.Request;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.handler.HttpHandlerFactory;
import io.purplejs.http.internal.error.DefaultExceptionHandler;
import io.purplejs.http.internal.handler.HttpHandlerFactoryImpl;

public final class HttpModule
    implements EngineModule
{
    private HttpHandlerFactoryImpl handlerFactory;

    @Override
    public void configure( final EngineBinder binder )
    {
        this.handlerFactory = new HttpHandlerFactoryImpl();

        binder.provider( Request.class, new RequestAccessor() );
        binder.instance( ExceptionHandler.class, new DefaultExceptionHandler() );
        binder.instance( HttpHandlerFactory.class, this.handlerFactory );

        binder.initializer( this::initialize );
    }

    private void initialize( final Engine engine )
    {
        this.handlerFactory.init( engine );
    }
}
