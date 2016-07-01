package io.purplejs.registry;

import java.util.Optional;
import java.util.function.Supplier;

public interface Registry
{
    <T> T getInstance( Class<T> type );

    <T> Optional<T> getOptional( Class<T> type );

    <T> Supplier<T> getProvider( Class<T> type );
}
