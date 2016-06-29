package io.purplejs.registry;

import java.util.function.Supplier;

import io.purplejs.impl.registry.RegistryBuilderImpl;

public interface RegistryBuilder
{
    <T> RegistryBuilder instance( Class<T> type, T instance );

    <T> RegistryBuilder supplier( Class<T> type, Supplier<T> supplier );

    Registry build();

    public static RegistryBuilder newBuilder()
    {
        return new RegistryBuilderImpl();
    }
}
