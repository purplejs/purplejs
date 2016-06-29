package io.purplejs.v2.injector;

import java.util.Map;

import javax.inject.Provider;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

final class InjectorBuilderImpl
    implements InjectorBuilder
{
    private final Map<Class, Binding> map;

    InjectorBuilderImpl()
    {
        this.map = Maps.newHashMap();
    }

    @Override
    public <T> InjectorBuilder instance( final Class<T> type, final T instance )
    {
        this.map.put( type, new InstanceBinding<>( instance ) );
        return this;
    }

    @Override
    public <T> InjectorBuilder provider( final Class<T> type, final Provider<T> provider )
    {
        this.map.put( type, new ProviderBinding<>( provider ) );
        return this;
    }

    @Override
    public Injector build()
    {
        return new InjectorImpl( ImmutableMap.copyOf( this.map ) );
    }
}
