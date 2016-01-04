package org.purplejs.registry;

import java.util.function.Supplier;

public final class RegistryBuilder
{
    public <T> RegistryBuilder add( final Class<T> type, final T instance )
    {
        return this;
    }

    public <T> RegistryBuilder add( final Class<T> type, final Supplier<T> supplier )
    {
        return this;
    }

    public Registry build()
    {
        return null;
    }
}
