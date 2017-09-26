package io.purplejs.core.exception;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourcePath;

/**
 * This exception indicates that a resource was not found or could not be resolved.
 */
public final class NotFoundException
    extends RuntimeException
{
    private final List<ResourcePath> scanned;

    /**
     * Constructs the exception with a message.
     *
     * @param message Message indicating the problem.
     */
    public NotFoundException( final String message )
    {
        super( message );
        this.scanned = Lists.newArrayList();
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

    /**
     * Return resources that has been scanned.
     *
     * @return resources scanned.
     */
    public List<ResourcePath> getScanned()
    {
        return this.scanned;
    }

    /**
     * Set scanned resources.
     *
     * @param scanned List of scanned resources.
     */
    public void setScanned( final List<ResourcePath> scanned )
    {
        this.scanned.clear();
        this.scanned.addAll( scanned );
    }
}
