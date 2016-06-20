package io.purplejs.impl.registry;

import io.purplejs.registry.Registry;

final class ChainRegistry
    extends AbstractRegistry
{
    private final Registry first;

    private final Registry next;

    public ChainRegistry( final Registry first, final Registry next )
    {
        this.first = first;
        this.next = next;
    }

    @Override
    public <T> T getOrNull( final Class<T> type )
    {
        final T service = this.first.getOrNull( type );
        if ( service != null )
        {
            return service;
        }

        return this.next.getOrNull( type );
    }
}
