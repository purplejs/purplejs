package org.purplejs.resource;

public abstract class ResourceException
    extends RuntimeException
{
    private final ResourcePath resource;

    public ResourceException( final ResourcePath resource )
    {
        this.resource = resource;
    }

    public final ResourcePath getResource()
    {
        return this.resource;
    }
}
