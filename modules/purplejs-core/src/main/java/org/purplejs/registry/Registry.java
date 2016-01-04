package org.purplejs.registry;

public interface Registry
{
    <T> T get( Class<T> type );

    <T> Iterable<T> getAll( Class<T> type );
}
