package org.purplejs.service;

import java.util.function.Supplier;

public interface ServiceRef<T>
    extends Supplier<T>, Iterable<T>
{
    Class<T> getType();

    boolean isEmpty();
}
