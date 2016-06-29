package io.purplejs.registry;

public interface Registry
{
    <T> Binding<T> get( Class<T> type );
}
