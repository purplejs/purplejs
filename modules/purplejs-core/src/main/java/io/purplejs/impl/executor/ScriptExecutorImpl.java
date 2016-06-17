package io.purplejs.impl.executor;

import java.util.Map;
import java.util.function.Function;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.SimpleBindings;

import io.purplejs.ScriptSettings;
import io.purplejs.impl.cache.ScriptExportsCache;
import io.purplejs.impl.util.NashornHelper;
import io.purplejs.impl.value.ScriptValueFactoryImpl;
import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptValue;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public final class ScriptExecutorImpl
    implements ScriptExecutor
{
    private ScriptEngine engine;

    private Bindings global;

    private ScriptSettings settings;

    private ScriptExportsCache exportsCache;

    private Map<ResourcePath, Object> mocks;

    private Map<ResourcePath, Runnable> finalizers;

    @Override
    public ScriptSettings getSettings()
    {
        return this.settings;
    }

    public void setEngine( final ScriptEngine engine )
    {
        this.engine = engine;
    }

    public void setSettings( final ScriptSettings settings )
    {
        this.settings = settings;
    }

    public void init()
    {
        this.mocks = Maps.newHashMap();
        this.finalizers = Maps.newHashMap();
        this.exportsCache = new ScriptExportsCache();
        this.global = this.engine.createBindings();
        this.global.putAll( this.settings.getGlobalVariables() );
        new CallFunction().register( this.global );
    }

    @Override
    public Object executeRequire( final ResourcePath path )
    {
        final Object mock = this.mocks.get( path );
        if ( mock != null )
        {
            return mock;
        }

        final Object cached = this.exportsCache.get( path );
        final Resource resource = loadIfNeeded( path, cached );
        if ( resource == null )
        {
            return cached;
        }

        final ScriptContextImpl context = new ScriptContextImpl( resource );
        context.setEngineScope( this.global );
        context.setGlobalScope( new SimpleBindings() );

        final ScriptObjectMirror func = (ScriptObjectMirror) doExecute( context, resource );
        final Object result = executeRequire( path, func );

        this.exportsCache.put( resource, result );
        return result;
    }

    @Override
    public ScriptValue newScriptValue( final Object value )
    {
        return new ScriptValueFactoryImpl( this::invokeMethod ).newValue( value );
    }

    private Object invokeMethod( final Object func, final Object... args )
    {
        try
        {
            return ( (Invocable) this.engine ).invokeMethod( this.global, CallFunction.NAME, func, args );
        }
        catch ( final Exception e )
        {
            throw ErrorHelper.handleError( e );
        }
    }

    private Resource loadIfNeeded( final ResourcePath key, final Object cached )
    {
        if ( cached == null )
        {
            return loadResource( key );
        }

        if ( !this.settings.isDevMode() )
        {
            return null;
        }

        final Resource resource = loadResource( key );
        if ( this.exportsCache.isExpired( resource ) )
        {
            runFinalizer( key );
            return resource;
        }

        return null;
    }

    private Resource loadResource( final ResourcePath key )
    {
        return this.settings.getResourceLoader().load( key );
    }

    private Object executeRequire( final ResourcePath script, final ScriptObjectMirror func )
    {
        try
        {
            final ExecutionContextImpl context = new ExecutionContextImpl( this, script );
            final Function<String, Object> requireFunc = context::require;
            final Function<String, ResourcePath> resolveFunc = context::resolve;

            final Object result = func.call( null, context, requireFunc, resolveFunc );
            return NashornHelper.unwrap( result );
        }
        catch ( final Exception e )
        {
            throw ErrorHelper.handleError( e );
        }
    }

    private Object doExecute( final ScriptContext context, final Resource script )
    {
        try
        {
            final String text = script.getBytes().asCharSource( Charsets.UTF_8 ).read();
            final String source = InitScriptReader.getScript( text );
            return this.engine.eval( source, context );
        }
        catch ( final Exception e )
        {
            throw ErrorHelper.handleError( e );
        }
    }

    @Override
    public void registerMock( final ResourcePath path, final Object value )
    {
        this.mocks.put( path, value );
    }

    @Override
    public void registerFinalizer( final ResourcePath path, final Runnable callback )
    {
        this.finalizers.put( path, callback );
    }

    private void runFinalizer( final ResourcePath path )
    {
        runFinalizer( path, this.finalizers.get( path ) );
    }

    private void runFinalizer( final ResourcePath path, final Runnable callback )
    {
        if ( callback != null )
        {
            callback.run();
        }
    }

    public void dispose()
    {
        this.finalizers.forEach( this::runFinalizer );
    }
}
