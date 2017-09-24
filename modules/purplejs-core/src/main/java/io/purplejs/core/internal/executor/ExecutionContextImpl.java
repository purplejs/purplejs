package io.purplejs.core.internal.executor;

import java.util.function.Supplier;

import io.purplejs.core.Engine;
import io.purplejs.core.Environment;
import io.purplejs.core.exception.NotFoundException;
import io.purplejs.core.internal.resolver.RequirePathResolver;
import io.purplejs.core.internal.resolver.ResourcePathResolver;
import io.purplejs.core.internal.resolver.StandardPathResolver;
import io.purplejs.core.internal.util.JsObjectConverter;
import io.purplejs.core.registry.Registry;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptValue;

final class ExecutionContextImpl
    implements ExecutionContext
{
    private final ScriptExecutor executor;

    private final ResourcePath resource;

    private final ResourcePathResolver requirePathResolver;

    private final ResourcePathResolver standardPathResolver;

    private final Environment environment;

    private final JsObjectConverter converter;

    private final ScriptLogger logger;

    ExecutionContextImpl( final ScriptExecutor executor, final ResourcePath resource )
    {
        this.executor = executor;
        this.resource = resource;
        this.environment = this.executor.getEnvironment();
        this.converter = new JsObjectConverter( this.executor.getNashornRuntime() );

        final ResourcePath dir = this.resource.resolve( ".." );
        this.requirePathResolver = new RequirePathResolver( this.environment.getResourceLoader(), dir );
        this.standardPathResolver = new StandardPathResolver( dir );
        this.logger = new ScriptLoggerImpl( this.resource );
    }

    @Override
    public ResourcePath getResource()
    {
        return this.resource;
    }

    @Override
    public Environment getEnvironment()
    {
        return this.environment;
    }

    @Override
    public Engine getEngine()
    {
        return this.environment.getInstance( Engine.class );
    }

    @Override
    public Registry getRegistry()
    {
        return this.environment;
    }

    @Override
    public ScriptLogger getLogger()
    {
        return this.logger;
    }

    @Override
    public void disposer( final Runnable runnable )
    {
        this.executor.registerDisposer( this.resource, runnable );
    }

    @Override
    public Object require( final String path )
    {
        final ResourcePath resolved = this.requirePathResolver.resolve( path );
        if ( resolved == null )
        {
            throw new NotFoundException( "Could not find [" + path + "] using base [" + this.resource + "]" );
        }

        return this.executor.executeRequire( resolved );
    }

    @Override
    public ResourcePath resolve( final String path )
    {
        return this.standardPathResolver.resolve( path );
    }

    @Override
    public ScriptValue toScriptValue( final Object value )
    {
        return this.executor.newScriptValue( value );
    }

    @Override
    public Object toNativeObject( final Object value )
    {
        return this.converter.toJs( value );
    }

    @Override
    public void registerMock( final String path, final Object value )
    {
        this.executor.registerMock( ResourcePath.from( path ), value );
    }

    private Class<?> forName( final String type )
        throws Exception
    {
        return Class.forName( type, true, this.environment.getClassLoader() );
    }

    @Override
    public Object getInstance( final String type )
        throws Exception
    {
        return this.environment.getInstance( forName( type ) );
    }

    @Override
    public Object getInstanceOrNull( final String type )
        throws Exception
    {
        return this.environment.getInstanceOrNull( forName( type ) );
    }

    @Override
    public Supplier<?> getProvider( final String type )
        throws Exception
    {
        return this.environment.getProvider( forName( type ) );
    }

    @Override
    public Object newBean( final String type )
        throws Exception
    {
        final Class<?> clz = forName( type );
        return clz.newInstance();
    }
}
