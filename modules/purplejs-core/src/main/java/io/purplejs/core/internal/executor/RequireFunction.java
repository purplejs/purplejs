package io.purplejs.core.internal.executor;

import java.util.function.Function;

import io.purplejs.core.Environment;
import io.purplejs.core.exception.NotFoundException;
import io.purplejs.core.internal.require.RequireResolverContextImpl;
import io.purplejs.core.require.RequireResolverContext;
import io.purplejs.core.resource.ResourcePath;

final class RequireFunction
    implements Function<String, Object>
{
    private final ScriptExecutor executor;

    private final Environment environment;

    private final ResourcePath resourceDir;

    RequireFunction( final ScriptExecutor executor, final ResourcePath resourceDir )
    {
        this.executor = executor;
        this.environment = this.executor.getEnvironment();
        this.resourceDir = resourceDir;
    }

    private RequireResolverContext newResolverContext()
    {
        return new RequireResolverContextImpl( this.environment.getResourceLoader(), this.resourceDir );
    }

    private Object require( final String path )
    {
        final RequireResolverContext context = newResolverContext();
        final ResourcePath resolved = this.environment.getRequireResolver().resolve( context, path );

        if ( resolved == null )
        {
            final NotFoundException ex = new NotFoundException( "Could not find [" + path + "] using base [" + this.resourceDir + "]" );
            ex.setScanned( context.getScanned() );
            throw ex;
        }

        return this.executor.executeRequire( resolved );
    }

    @Override
    public Object apply( final String path )
    {
        return require( path );
    }
}
