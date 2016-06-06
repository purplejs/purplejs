package org.purplejs.registry;

public interface LookupProvider
{
    <T> T lookup( Class<T> type );
}
