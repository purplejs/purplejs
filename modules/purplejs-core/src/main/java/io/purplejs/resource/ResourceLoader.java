package io.purplejs.resource;

public interface ResourceLoader
{
    default Resource load( final ResourcePath path )
    {
        final Resource result = loadOrNull( path );
        if ( result != null )
        {
            return result;
        }

        throw new ResourceNotFoundException( path );
    }

    default boolean exists( final ResourcePath path )
    {
        return loadOrNull( path ) != null;
    }

    Resource loadOrNull( ResourcePath path );
}
