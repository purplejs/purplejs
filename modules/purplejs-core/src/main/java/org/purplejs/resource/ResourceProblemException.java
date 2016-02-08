package org.purplejs.resource;

public final class ResourceProblemException
    extends ResourceException
{
    public ResourceProblemException( final ResourcePath resource )
    {
        super( resource );
    }
}
