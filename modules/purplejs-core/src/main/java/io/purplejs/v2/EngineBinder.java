package io.purplejs.v2;

import javax.inject.Provider;

public interface EngineBinder
{
    <T> EngineBinder instance( Class<T> type, T instance );

    <T> EngineBinder provider( Class<T> type, Provider<T> provider );

    EngineBinder globalVariable( String name, Object value );

    EngineBinder config( String name, String value );
}
