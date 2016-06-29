package io.purplejs.v2.injector;

import javax.inject.Provider;

public interface InjectorBuilder
{
    <T> InjectorBuilder instance( Class<T> type, T instance );

    <T> InjectorBuilder provider( Class<T> type, Provider<T> provider );

    Injector build();

    public static InjectorBuilder newBuilder()
    {
        return new InjectorBuilderImpl();
    }
}
