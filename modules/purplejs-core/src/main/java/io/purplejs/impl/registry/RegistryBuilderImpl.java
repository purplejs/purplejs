package io.purplejs.impl.registry;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import io.purplejs.registry.Registry;
import io.purplejs.registry.RegistryBuilder;

public final class RegistryBuilderImpl
    implements RegistryBuilder
{
    private final Map<Class, Supplier> map;

    public RegistryBuilderImpl()
    {
        this.map = Maps.newHashMap();
    }

    @Override
    public <T> RegistryBuilder instance( final Class<T> type, final T instance )
    {
        return provider( type, () -> instance );
    }

    @Override
    public <T> RegistryBuilder provider( final Class<T> type, final Supplier<T> supplier )
    {
        this.map.put( type, supplier );
        return this;
    }

    @Override
    public Registry build()
    {
        return new RegistryImpl( ImmutableMap.copyOf( this.map ) );
    }
}
