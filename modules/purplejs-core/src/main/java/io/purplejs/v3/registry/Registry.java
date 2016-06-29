package io.purplejs.v3.registry;

import java.util.function.Supplier;

public interface Registry
{
    <T> T getInstance( Class<T> type );

    <T> Supplier<T> getSupplier( Class<T> type );

    <T> T newInstance( Class<T> type );
}
