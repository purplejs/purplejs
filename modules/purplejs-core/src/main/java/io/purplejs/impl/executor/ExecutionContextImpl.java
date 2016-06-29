package io.purplejs.impl.executor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.base.Throwables;

import io.purplejs.Environment;
import io.purplejs.context.ExecutionContext;
import io.purplejs.impl.util.JsObjectConverter;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptValue;

final class ExecutionContextImpl
    implements ExecutionContext
{
    private final ScriptExecutor executor;

    private final ResourcePath resource;

    private final ResourceResolver resourceResolver;

    private final Environment environment;

    ExecutionContextImpl( final ScriptExecutor executor, final ResourcePath resource )
    {
        this.executor = executor;
        this.resource = resource;
        this.environment = this.executor.getEnvironment();
        this.resourceResolver = new ResourceResolver( this.environment.getResourceLoader(), this.resource );
    }

    @Override
    public ResourcePath getResource()
    {
        return this.resource;
    }

    @Override
    public void disposer( final Runnable runnable )
    {
        this.executor.registerDisposer( this.resource, runnable );
    }

    @Override
    public Object require( final String path )
    {
        final ResourcePath key = this.resourceResolver.resolveJs( path );
        return this.executor.executeRequire( key );
    }

    @Override
    public ResourcePath resolve( final String path )
    {
        return this.resourceResolver.resolve( path );
    }

    @Override
    public ScriptValue toScriptValue( final Object value )
    {
        return this.executor.newScriptValue( value );
    }

    @Override
    public Object toNativeObject( final Object value )
    {
        return JsObjectConverter.toJs( value );
    }

    @Override
    public void registerMock( final String path, final Object value )
    {
        this.executor.registerMock( ResourcePath.from( path ), value );
    }

    private Class<?> forName( final String type )
    {
        try
        {
            return Class.forName( type, true, getClassLoader() );
        }
        catch ( final Exception e )
        {
            throw Throwables.propagate( e );
        }
    }

    @Override
    public Object getInstance( final String type )
    {
        return getInstance( forName( type ) );
    }

    @Override
    public Supplier<?> getSupplier( final String type )
    {
        return getSupplier( forName( type ) );
    }

    @Override
    public Optional<?> getOptional( final String type )
    {
        return getOptional( forName( type ) );
    }

    @Override
    public <T> T newBean( final Class<T> type )
    {
        try
        {
            return type.newInstance();
        }
        catch ( final Exception e )
        {
            throw Throwables.propagate( e );
        }
    }

    @Override
    public Object newBean( final String type )
    {
        return newBean( forName( type ) );
    }

    @Override
    public boolean isDevMode()
    {
        return this.environment.isDevMode();
    }

    @Override
    public ResourceLoader getResourceLoader()
    {
        return this.environment.getResourceLoader();
    }

    @Override
    public ClassLoader getClassLoader()
    {
        return this.environment.getClassLoader();
    }

    @Override
    public Map<String, String> getConfig()
    {
        return this.environment.getConfig();
    }

    @Override
    public Map<String, Object> getGlobalVariables()
    {
        return this.environment.getGlobalVariables();
    }

    @Override
    public <T> Optional<T> getOptional( final Class<T> type )
    {
        return this.environment.getOptional( type );
    }

    @Override
    public <T> T getInstance( final Class<T> type )
    {
        return this.environment.getInstance( type );
    }

    @Override
    public <T> Supplier<T> getSupplier( final Class<T> type )
    {
        return this.environment.getSupplier( type );
    }
}
