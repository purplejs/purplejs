package io.purplejs.v3.registry;

import java.util.function.Supplier;

import com.google.common.base.Throwables;

final class RegistryImpl
    implements Registry
{
    Injector injector;

    Bindings bindings;

    void init()
    {
        this.bindings.init( this.injector );
    }

    @Override
    public <T> T getInstance( final Class<T> type )
    {
        return this.bindings.getBinding( type ).get();
    }

    @Override
    public <T> Supplier<T> getSupplier( final Class<T> type )
    {
        return this.bindings.getBinding( type );
    }

    @Override
    public <T> T newInstance( final Class<T> type )
    {
        try
        {
            final T instance = type.newInstance();
            this.injector.inject( instance );
            return instance;
        }
        catch ( final Exception e )
        {
            throw Throwables.propagate( e );
        }
    }
}
