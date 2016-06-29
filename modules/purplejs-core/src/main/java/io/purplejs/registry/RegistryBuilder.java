package io.purplejs.registry;

import java.util.function.Supplier;

public interface RegistryBuilder
{
    <T> RegistryBuilder bind( Class<T> type, T instance );

    <T> RegistryBuilder bind( Class<T> type, Supplier<T> supplier );
}
