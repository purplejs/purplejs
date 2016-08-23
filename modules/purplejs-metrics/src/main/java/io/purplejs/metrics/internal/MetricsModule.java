package io.purplejs.metrics.internal;

import com.codahale.metrics.MetricRegistry;

import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineModule;
import io.purplejs.metrics.MetricsAccessor;

public final class MetricsModule
    implements EngineModule
{
    @Override
    public void configure( final EngineBinder binder )
    {
        binder.instance( MetricRegistry.class, MetricsAccessor.getRegistry() );
    }
}
