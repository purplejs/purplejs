package org.purplejs.impl.executor;

import java.util.Map;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;

import org.purplejs.impl.cache.ScriptExportsCache;
import org.purplejs.impl.function.CallFunction;
import org.purplejs.impl.function.ScriptFunctions;
import org.purplejs.impl.util.NashornHelper;
import org.purplejs.impl.value.ScriptValueFactoryImpl;
import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptValue;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public final class ScriptExecutorImpl
    implements ScriptExecutor
{
    private ScriptEngine engine;

    private Bindings global;

    private boolean devMode;

    private ScriptExportsCache exportsCache;

    private ResourceLoader resourceLoader;

    private Map<String, Object> mocks;

    @Override
    public Object executeRequire( final ResourcePath path )
    {
        final Object mock = this.mocks.get( path.getPath() );
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

        /*
        final ScriptContextImpl context = new ScriptContextImpl( resource, this.scriptSettings );
        context.setEngineScope( this.global );
        context.setGlobalScope( new SimpleBindings() );

        final ScriptObjectMirror func = (ScriptObjectMirror) doExecute( context, resource );
        final Object result = executeRequire( key, func );

        this.exportsCache.put( resource, result );
        return result;
        */

        return null;
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

        if ( !this.devMode )
        {
            return null;
        }

        final Resource resource = loadResource( key );
        if ( this.exportsCache.isExpired( resource ) )
        {
            return resource;
        }

        return null;
    }

    private Resource loadResource( final ResourcePath key )
    {
        return this.resourceLoader.load( key );
    }

    private Object executeRequire( final ResourcePath script, final ScriptObjectMirror func )
    {
        try
        {
            final ScriptFunctions functions = new ScriptFunctions( script, this );
            final Object result = func.call( null, functions.getLog(), functions.getRequire(), functions.getResolve(), functions );
            return NashornHelper.unwrap( result );
        }
        catch ( final Exception e )
        {
            throw ErrorHelper.handleError( e );
        }
    }
}
