package io.purplejs.registry;

import java.util.Optional;
import java.util.function.Supplier;

public interface Registry
{
    <T> T get( Class<T> type );

    <T> T getOrNull( Class<T> type );

    <T> Optional<T> getOptional( Class<T> type );

    <T> Supplier<T> getSupplier( Class<T> type );
}
