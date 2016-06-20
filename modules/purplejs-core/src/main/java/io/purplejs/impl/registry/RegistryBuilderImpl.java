package io.purplejs.impl.registry;

import java.util.function.Supplier;

import io.purplejs.registry.LookupProvider;
import io.purplejs.registry.RegistryBuilder;

public final class RegistryBuilderImpl
    implements RegistryBuilder
{
    @Override
    public <T> RegistryBuilder instance( final Class<T> type, final T instance )
    {
        return null;
    }

    @Override
    public <T> RegistryBuilder supplier( final Class<T> type, final Supplier<T> supplier )
    {
        return null;
    }

    @Override
    public RegistryBuilder lookupProvider( final LookupProvider lookupProvider )
    {
        return null;
    }
}
