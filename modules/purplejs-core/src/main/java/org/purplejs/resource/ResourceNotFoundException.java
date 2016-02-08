package org.purplejs.resource;

public final class ResourceNotFoundException
    extends ResourceException
{
    public ResourceNotFoundException( final ResourcePath resource )
    {
        super( resource );
    }
}
