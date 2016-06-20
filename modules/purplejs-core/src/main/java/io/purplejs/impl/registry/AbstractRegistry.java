package io.purplejs.impl.registry;

import java.util.Optional;
import java.util.function.Supplier;

import io.purplejs.registry.Registry;

abstract class AbstractRegistry
    implements Registry
{
    @Override
    public final <T> T get( final Class<T> type )
    {
        final T service = getOrNull( type );
        if ( service != null )
        {
            return service;
        }

        throw new IllegalArgumentException( String.format( "Binding of type [%s] not found.", type.getName() ) );
    }

    @Override
    public final <T> Optional<T> getOptional( final Class<T> type )
    {
        final T service = getOrNull( type );
        return Optional.ofNullable( service );
    }

    @Override
    public final <T> Supplier<T> getSupplier( final Class<T> type )
    {
        return () -> get( type );
    }
}
