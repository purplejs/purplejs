package org.purplejs.registry;

import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

final class RegistryImpl
    implements Registry
{
    protected final ImmutableMap<Class, Binding> map;

    public RegistryImpl( final ImmutableMap<Class, Binding> map )
    {
        this.map = map;
    }

    @Override
    public <T> T get( final Class<T> type )
    {
        return getSupplier( type ).get();
    }

    @Override
    public <T> Supplier<T> getSupplier( final Class<T> type )
    {
        return getBinding( type );
    }

    @Override
    public <T> Iterable<T> getAll( final Class<T> type )
    {
        return getBinding( type );
    }

    @Override
    public <T> Optional<T> getOptional( final Class<T> type )
    {
        final Binding<T> binding = getBinding( type );
        return binding.isEmpty() ? Optional.empty() : Optional.of( binding.get() );
    }

    @Override
    public <T> Binding<T> getBinding( final Class<T> type )
    {
        final Binding<T> binding = typecastBiding( this.map.get( type ) );
        return binding != null ? binding : BindingImpl.empty( type );
    }

    @Override
    public Iterable<Binding> getBindings()
    {
        return this.map.values();
    }

    @SuppressWarnings("unchecked")
    private <T> Binding<T> typecastBiding( final Binding instance )
    {
        return (Binding<T>) instance;
    }
}
