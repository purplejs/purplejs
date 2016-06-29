package io.purplejs.v3.registry;

import java.util.function.Supplier;

public final class RegistryBuilder
{
    private final Bindings bindings;

    private Injector injector;

    public RegistryBuilder()
    {
        this.bindings = new Bindings();
    }

    public RegistryBuilder injector( final Injector injector )
    {
        this.injector = injector;
        return this;
    }

    public <T> RegistryBuilder instance( final Class<T> type, final T instance )
    {
        this.bindings.instance( type, instance );
        return this;
    }

    public <T> RegistryBuilder supplier( final Class<T> type, final Supplier<T> supplier )
    {
        this.bindings.supplier( type, supplier );
        return this;
    }

    public Registry build()
    {
        final RegistryImpl registry = new RegistryImpl();
        registry.bindings = this.bindings;
        registry.injector = this.injector;

        registry.init();
        return registry;
    }
}
