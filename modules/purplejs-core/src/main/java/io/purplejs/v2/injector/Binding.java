package io.purplejs.v2.injector;

import javax.inject.Provider;

interface Binding<T>
    extends Provider<T>
{
    void inject( Injector injector );
}
