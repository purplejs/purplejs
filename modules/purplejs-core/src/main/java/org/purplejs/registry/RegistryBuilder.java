package org.purplejs.registry;

public final class RegistryBuilder
{
    public <T> RegistryBuilder add( final Class<T> type, final T instance )
    {
        return this;
    }

    public RegistryBuilder join( Registry registry )
    {
        return this;
    }

    public Registry build()
    {
        return null;
    }
}
