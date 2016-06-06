package org.purplejs.service;

public interface ServiceRegistry
{
    <T> ServiceRef<T> lookup( Class<T> type );
}
