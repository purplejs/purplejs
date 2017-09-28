package io.purplejs.core.internal.executor;

import java.util.function.Supplier;

import io.purplejs.core.Engine;
import io.purplejs.core.Environment;
import io.purplejs.core.exception.NotFoundException;
import io.purplejs.core.inject.BeanInjector;
import io.purplejs.core.internal.inject.InjectorContextImpl;
import io.purplejs.core.internal.resolver.ResourceResolverContextImpl;
import io.purplejs.core.internal.util.JsObjectConverter;
import io.purplejs.core.registry.Registry;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.resource.ResourceResolverContext;
import io.purplejs.core.resource.ResourceResolverMode;
import io.purplejs.core.value.ScriptValue;

final class ExecutionContextImpl
    implements ExecutionContext
{
    private final ScriptExecutor executor;

    private final ResourcePath resource;

    private final Environment environment;

    private final JsObjectConverter converter;

    private final ScriptLogger logger;

    private final ResourcePath resourceDir;

    ExecutionContextImpl( final ScriptExecutor executor, final ResourcePath resource )
    {
        this.executor = executor;
        this.resource = resource;
        this.environment = this.executor.getEnvironment();
        this.converter = new JsObjectConverter( this.executor.getNashornRuntime() );
        this.resourceDir = this.resource.resolve( ".." );
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

    private ResourceResolverContext newResolverContext( final ResourceResolverMode mode )
    {
        return new ResourceResolverContextImpl( mode, this.environment.getResourceLoader(), this.resourceDir );
    }

    @Override
    public Object require( final String path )
    {
        final ResourceResolverContext context = newResolverContext( ResourceResolverMode.REQUIRE );
        final ResourcePath resolved = this.environment.getResourceResolver().resolve( context, path );

        if ( resolved == null )
        {
            final NotFoundException ex = new NotFoundException( "Could not find [" + path + "] using base [" + this.resourceDir + "]" );
            ex.setScanned( context.getScanned() );
            throw ex;
        }

        return this.executor.executeRequire( resolved );
    }

    @Override
    public ResourcePath resolve( final String path )
    {
        final ResourceResolverContext context = newResolverContext( ResourceResolverMode.SIMPLE );
        return this.environment.getResourceResolver().resolve( context, path );
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
        return inject( clz.newInstance() );
    }

    private Object inject( final Object instance )
    {
        final BeanInjector injector = this.getEnvironment().getBeanInjector();
        final InjectorContextImpl context = new InjectorContextImpl( this.getEngine(), this.resource, instance );
        injector.inject( context );
        return context.getInstance();
    }
}
