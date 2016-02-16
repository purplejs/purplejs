package org.purplejs.registry;

import java.util.Optional;
import java.util.function.Supplier;

public interface Registry
{
    <T> T get( Class<T> type );

    <T> Supplier<T> getSupplier( Class<T> type );

    <T> Iterable<T> getAll( Class<T> type );

    <T> Optional<T> getOptional( Class<T> type );

    <T> Binding<T> getBinding( Class<T> type );

    Iterable<Binding> getBindings();
}
