package io.purplejs.core.exception;

import io.purplejs.core.resource.ResourcePath;

/**
 * This exception indicates that a resource was not found or could not be resolved.
 */
public final class NotFoundException
    extends RuntimeException
{
    /**
     * Constructs the exception with a message.
     *
     * @param message Message indicating the problem.
     */
    public NotFoundException( final String message )
    {
        super( message );
    }

    /**
     * Constructs the exception with a resource path.
     *
     * @param path Path indicating which resource was not found.
     */
    public NotFoundException( final ResourcePath path )
    {
        this( String.format( "Resource [%s] not found", path.toString() ) );
    }
}
