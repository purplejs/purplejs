package org.purplejs.impl.executor;

import org.purplejs.resource.ResourcePath;

public final class ExecutionContext
{
    private final ScriptExecutor executor;

    private final ResourcePath resource;

    private ResourceResolver resourceResolver;

    public ExecutionContext( final ScriptExecutor executor, final ResourcePath resource )
    {
        this.executor = executor;
        this.resource = resource;
        this.resourceResolver = new ResourceResolver( this.executor.getSettings().getResourceLoader(), this.resource );
    }

    public Object newBean( final String type )
        throws Exception
    {
        return null;
    }

    public void finalize( final Runnable runnable )
    {

    }

    public ResourcePath getResource()
    {
        return this.resource;
    }

    public Object require( final String path )
    {
        final ResourcePath key = this.resourceResolver.resolveJs( path );
        return this.executor.executeRequire( key );
    }

    public ResourcePath resolve( final String path )
    {
        return this.resourceResolver.resolve( path );
    }
}
