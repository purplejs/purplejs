package io.purplejs.v3.registry;

import java.util.function.Supplier;

interface Binding<T>
    extends Supplier<T>
{
    void init( Injector injector );
}
