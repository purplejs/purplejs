package io.purplejs.v2.injector;

import javax.inject.Provider;

public interface Injector
{
    <T> T instance( Class<T> type );

    <T> Provider<T> provider( Class<T> type );

    void inject( Object instance );
}
