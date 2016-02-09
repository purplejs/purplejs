package org.purplejs.resource;

public final class ResourceNotFoundException
    extends ResourceException
{
    public ResourceNotFoundException( final ResourcePath resource )
    {
        super( resource );
    }

    @Override
    public String getMessage()
    {
        return String.format( "Resource [%s] not found", getResource().toString() );
    }
}
