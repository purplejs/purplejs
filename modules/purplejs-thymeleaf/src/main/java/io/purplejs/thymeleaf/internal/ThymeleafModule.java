package io.purplejs.thymeleaf.internal;

import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineModule;
import io.purplejs.thymeleaf.ThymeleafService;

public final class ThymeleafModule
    implements EngineModule
{
    @Override
    public void configure( final EngineBinder binder )
    {
        final ThymeleafServiceImpl service = new ThymeleafServiceImpl();
        binder.initializer( service::initialize );
        binder.instance( ThymeleafService.class, service );
    }
}
