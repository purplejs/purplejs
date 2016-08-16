package io.purplejs.core.registry;

import java.util.function.Supplier;

public interface RegistryBuilder
{
    <T> RegistryBuilder instance( Class<T> type, T instance );

    <T> RegistryBuilder provider( Class<T> type, Supplier<T> supplier );

    Registry build();

    static RegistryBuilder newBuilder()
    {
        return new RegistryBuilderImpl();
    }
}
