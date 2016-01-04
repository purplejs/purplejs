package org.purplejs.registry;

import java.util.function.Supplier;

public interface Registry
{
    <T> T get( Class<T> type );

    <T> Supplier<T> getSupplier( Class<T> type );

    <T> Iterable<T> getAll( Class<T> type );

    <T> Iterable<Supplier<T>> getAllSuppliers( Class<T> type );
}
