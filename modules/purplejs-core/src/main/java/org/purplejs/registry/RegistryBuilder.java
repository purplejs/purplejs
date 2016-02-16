package org.purplejs.registry;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public final class RegistryBuilder
{
    private final Map<Class, Binding> map;

    private RegistryBuilder()
    {
        this.map = Maps.newHashMap();
    }

    public <T> RegistryBuilder instance( final Class<T> type, final T instance )
    {
        return supplier( type, () -> instance );
    }

    @SuppressWarnings("unchecked")
    public <T> RegistryBuilder supplier( final Class<T> type, final Supplier<T> supplier )
    {
        final BindingImpl old = (BindingImpl) this.map.get( type );
        return binding( new BindingImpl<>( type, supplier, old ) );
    }

    private RegistryBuilder binding( final Binding binding )
    {
        this.map.put( binding.getType(), binding );
        return this;
    }

    public RegistryBuilder join( final Registry registry )
    {
        registry.getBindings().forEach( this::binding );
        return this;
    }

    public Registry build()
    {
        return new RegistryImpl( ImmutableMap.copyOf( this.map ) );
    }

    public static RegistryBuilder create()
    {
        return new RegistryBuilder();
    }
}
