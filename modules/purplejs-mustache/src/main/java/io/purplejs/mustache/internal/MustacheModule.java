package io.purplejs.mustache.internal;

import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineModule;
import io.purplejs.mustache.MustacheService;

public final class MustacheModule
    implements EngineModule
{
    @Override
    public void configure( final EngineBinder binder )
    {
        final MustacheServiceImpl service = new MustacheServiceImpl();
        binder.initializer( service::initialize );
        binder.instance( MustacheService.class, service );
    }
}
