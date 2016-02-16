package org.purplejs.registry;

import java.util.function.Supplier;

public interface Binding<T>
    extends Supplier<T>, Iterable<T>
{
    Class<T> getType();

    boolean isEmpty();
}
