package io.purplejs.impl.registry;

import io.purplejs.registry.LookupProvider;

final class LookupProviderRegistry
    extends AbstractRegistry
{
    private final LookupProvider provider;

    public LookupProviderRegistry( final LookupProvider provider )
    {
        this.provider = provider;
    }

    @Override
    public <T> T getOrNull( final Class<T> type )
    {
        return this.provider.lookup( type );
    }
}
