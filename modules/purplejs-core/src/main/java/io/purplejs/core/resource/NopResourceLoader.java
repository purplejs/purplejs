package io.purplejs.core.resource;

final class NopResourceLoader
    implements ResourceLoader
{
    @Override
    public Resource loadOrNull( final ResourcePath path )
    {
        return null;
    }
}
