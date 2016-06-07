package org.purplejs.impl.executor;

import java.util.Map;

import org.purplejs.engine.ExecutionContext;
import org.purplejs.engine.ScriptSettings;
import org.purplejs.impl.util.JsObjectConverter;
import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptValue;

public final class ExecutionContextImpl
    implements ExecutionContext
{
    private final ScriptExecutor executor;

    private final ResourcePath resource;

    private ResourceResolver resourceResolver;

    public ExecutionContextImpl( final ScriptExecutor executor, final ResourcePath resource )
    {
        this.executor = executor;
        this.resource = resource;
        this.resourceResolver = new ResourceResolver( this.executor.getSettings().getResourceLoader(), this.resource );
    }

    @Override
    public ResourceLoader getLoader()
    {
        return this.executor.getSettings().getResourceLoader();
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
}
