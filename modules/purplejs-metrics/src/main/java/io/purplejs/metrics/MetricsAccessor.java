package io.purplejs.metrics;

import com.codahale.metrics.MetricRegistry;

public final class MetricsAccessor
{
    private final static MetricRegistry REGISTRY = new MetricRegistry();

    public static MetricRegistry getRegistry()
    {
        return REGISTRY;
    }
}
