package io.purplejs.impl.executor;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.base.Throwables;

import io.purplejs.ExecutionContext;
import io.purplejs.ScriptSettings;
import io.purplejs.impl.util.JsObjectConverter;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptValue;

final class ExecutionContextImpl
    implements ExecutionContext
{
    private final ScriptExecutor executor;

    private final ResourcePath resource;

    private ResourceResolver resourceResolver;

    private final Supplier<Object> commandSupplier;

    ExecutionContextImpl( final ScriptExecutor executor, final ResourcePath resource, final Supplier<Object> commandSupplier )
    {
        this.executor = executor;
        this.resource = resource;
        this.resourceResolver = new ResourceResolver( this.executor.getSettings().getResourceLoader(), this.resource );
        this.commandSupplier = commandSupplier;
    }

    @Override
    public ResourceLoader getLoader()
    {
        return this.executor.getSettings().getResourceLoader();
    }

    @Override
    public Object getCommand()
    {
        return this.commandSupplier.get();
    }

    @Override
    public Object newBean( final String type )
    {
        try
        {
            final Class<?> clz = Class.forName( type, true, this.executor.getSettings().getClassLoader() );
            return clz.newInstance();
        }
        catch ( final Exception e )
        {
            throw new RuntimeException( "Failed to create instance of [" + type + "]", e );
        }
    }

    @Override
    public ResourcePath getResource()
    {
        return this.resource;
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

    @Override
    public ScriptSettings getSettings()
    {
        return this.executor.getSettings();
    }

    @Override
    public void finalizer( final Runnable runnable )
    {
        this.executor.registerFinalizer( this.resource, runnable );
    }

    @Override
    public Map<String, String> getConfig()
    {
        return this.executor.getSettings().getConfig();
    }

    @Override
    public <T> Supplier<T> getSupplier( final Class<T> type )
    {
        try
        {
            final T instance = type.newInstance();
            return () -> instance;
        }
        catch ( final Exception e )
        {
            throw Throwables.propagate( e );
        }
    }

    @Override
    public Supplier<?> getSupplier( final String type )
    {
        try
        {
            return getSupplier( Class.forName( type ) );
        }
        catch ( final Exception e )
        {
            throw Throwables.propagate( e );
        }
    }
}
