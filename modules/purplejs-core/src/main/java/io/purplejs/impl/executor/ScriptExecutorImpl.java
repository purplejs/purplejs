package io.purplejs.impl.executor;

import java.util.Map;
import java.util.function.Function;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.SimpleBindings;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

import io.purplejs.Environment;
import io.purplejs.impl.cache.ScriptExportsCache;
import io.purplejs.impl.nashorn.NashornRuntime;
import io.purplejs.impl.util.ErrorHelper;
import io.purplejs.impl.value.ScriptValueFactory;
import io.purplejs.impl.value.ScriptValueFactoryImpl;
import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptValue;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public final class ScriptExecutorImpl
    implements ScriptExecutor
{
    private ScriptEngine engine;

    private Environment environment;

    private ScriptExportsCache exportsCache;

    private Map<ResourcePath, Object> mocks;

    private Map<ResourcePath, Runnable> disposers;

    private ScriptValueFactory scriptValueFactory;

    private NashornRuntime nashornRuntime;

    private Bindings global;

    public ScriptExecutorImpl()
    {
        this.global = new SimpleBindings();
    }

    @Override
    public Environment getEnvironment()
    {
        return this.environment;
    }

    public void setNashornRuntime( final NashornRuntime nashornRuntime )
    {
        this.nashornRuntime = nashornRuntime;
    }

    public void setEnvironment( final Environment environment )
    {
        this.environment = environment;
    }

    public void init()
    {
        this.mocks = Maps.newHashMap();
        this.disposers = Maps.newHashMap();
        this.exportsCache = new ScriptExportsCache();
        this.scriptValueFactory = new ScriptValueFactoryImpl( this.nashornRuntime );

        this.engine = this.nashornRuntime.getEngine();
        this.engine.setBindings( this.global, ScriptContext.GLOBAL_SCOPE );
    }

    public void addGlobalVariables( final Map<String, Object> variables )
    {
        this.global.putAll( variables );
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

        final Bindings bindings = new SimpleBindings();
        bindings.put( ScriptEngine.FILENAME, path.toString() );

        final ScriptObjectMirror func = (ScriptObjectMirror) doExecute( bindings, resource );
        final Object result = executeRequire( path, func );

        this.exportsCache.put( resource, result );
        return result;
    }

    @Override
    public ScriptValue newScriptValue( final Object value )
    {
        return this.scriptValueFactory.newValue( value );
    }

    private Resource loadIfNeeded( final ResourcePath key, final Object cached )
    {
        if ( cached == null )
        {
            return loadResource( key );
        }

        if ( !this.environment.isDevMode() )
        {
            return null;
        }

        final Resource resource = loadResource( key );
        if ( this.exportsCache.isExpired( resource ) )
        {
            runDisposer( key );
            return resource;
        }

        return null;
    }

    private Resource loadResource( final ResourcePath key )
    {
        return this.environment.getResourceLoader().load( key );
    }

    private Object executeRequire( final ResourcePath script, final ScriptObjectMirror func )
    {
        try
        {
            final ExecutionContextImpl context = new ExecutionContextImpl( this, script );
            final Function<String, Object> requireFunc = context::require;
            final Function<String, ResourcePath> resolveFunc = context::resolve;

            return func.call( null, context, requireFunc, resolveFunc );
        }
        catch ( final Exception e )
        {
            throw ErrorHelper.INSTANCE.handleError( e );
        }
    }

    private Object doExecute( final Bindings bindings, final Resource script )
    {
        try
        {
            final String text = script.getBytes().asCharSource( Charsets.UTF_8 ).read();
            final String source = InitScriptReader.getScript( text );
            return this.engine.eval( source, bindings );
        }
        catch ( final Exception e )
        {
            throw ErrorHelper.INSTANCE.handleError( e );
        }
    }

    @Override
    public void registerMock( final ResourcePath path, final Object value )
    {
        this.mocks.put( path, value );
    }

    @Override
    public void registerDisposer( final ResourcePath path, final Runnable callback )
    {
        this.disposers.put( path, callback );
    }

    private void runDisposer( final ResourcePath path )
    {
        runDisposer( this.disposers.get( path ) );
    }

    private void runDisposer( final Runnable callback )
    {
        if ( callback != null )
        {
            callback.run();
        }
    }

    public void dispose()
    {
        this.disposers.values().forEach( this::runDisposer );
    }

    @Override
    public NashornRuntime getNashornRuntime()
    {
        return null;
    }
}
