package io.purplejs.v3.registry;

import java.util.function.Supplier;

final class SupplierBinding<T>
    implements Binding<T>
{
    private final Supplier<T> supplier;

    private Injector injector;

    SupplierBinding( final Supplier<T> supplier )
    {
        this.supplier = supplier;
    }

    @Override
    public void init( final Injector injector )
    {
        this.injector = injector;
    }

    @Override
    public T get()
    {
        final T instance = this.supplier.get();
        if ( instance != null )
        {
            this.injector.inject( instance );
        }

        return instance;
    }
}
