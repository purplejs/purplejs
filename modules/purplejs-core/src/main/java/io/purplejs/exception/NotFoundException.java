package io.purplejs.exception;

import io.purplejs.resource.ResourcePath;

public final class NotFoundException
    extends RuntimeException
{
    public NotFoundException( final String message )
    {
        super( message );
    }

    public NotFoundException( final ResourcePath path )
    {
        this( String.format( "Resource [%s] not found", path.toString() ) );
    }
}
