package io.purplejs.core.internal.executor;

import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.SimpleBindings;

import com.google.common.collect.Maps;

import io.purplejs.core.Environment;
import io.purplejs.core.RunMode;
import io.purplejs.core.internal.cache.ScriptExportsCache;
import io.purplejs.core.internal.nashorn.NashornRuntime;
import io.purplejs.core.internal.util.ErrorHelper;
import io.purplejs.core.internal.util.IOHelper;
import io.purplejs.core.internal.value.ScriptExportsImpl;
import io.purplejs.core.internal.value.ScriptValueFactory;
import io.purplejs.core.internal.value.ScriptValueFactoryImpl;
import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;
import io.purplejs.core.value.ScriptValue;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public final class ScriptExecutorImpl
    implements ScriptExecutor
{
    private final static String PRE_SCRIPT = "(function(require, log, exports, module) { ";

    private final static String POST_SCRIPT = "\n});";

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

        this.global.put( "__executionContext", new ExecutionContextImpl( this ) );
    }

    public void addGlobalVariables( final Map<String, Object> variables )
    {
        this.global.putAll( variables );
    }

    @Override
    public ScriptExports executeMain( final ResourcePath path )
    {
        expireCacheIfNeeded();

        final Object exports = executeRequire( path );
        final ScriptValue value = newScriptValue( exports );
        return new ScriptExportsImpl( path, value );
    }

    private void expireCacheIfNeeded()
    {
        if ( RunMode.get() != RunMode.DEV )
        {
            return;
        }

        if ( this.exportsCache.isExpired() )
        {
            this.exportsCache.clear();
            runDisposers();
        }
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

        final Object result = requireJsOrJson( resource );
        this.exportsCache.put( resource, result );
        return result;
    }

    private Object requireJsOrJson( final Resource resource )
    {
        final String ext = resource.getPath().getExtension();
        if ( ext.equals( "json" ) )
        {
            return requireJson( resource );
        }

        return requireJs( resource );
    }

    private Object requireJs( final Resource resource )
    {
        final ResourcePath path = resource.getPath();

        final Bindings bindings = new SimpleBindings();
        bindings.put( ScriptEngine.FILENAME, path.toString() );

        final ScriptObjectMirror func = doExecute( bindings, resource );
        return executeRequire( path, func );
    }

    private Object requireJson( final Resource resource )
    {
        try
        {
            final String text = IOHelper.readString( resource.getBytes() );
            return this.nashornRuntime.parseJson( text );
        }
        catch ( final Exception e )
        {
            throw ErrorHelper.INSTANCE.handleError( e );
        }
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
            final ScriptObjectMirror exports = this.nashornRuntime.newJsObject();

            final ScriptObjectMirror module = this.nashornRuntime.newJsObject();
            module.put( "id", script.toString() );
            module.put( "exports", exports );

            final RequireFunction requireFunc = new RequireFunction( this, script.getParent() );
            final ScriptLoggerImpl logger = new ScriptLoggerImpl( script );

            func.call( exports, requireFunc, logger, exports, module );
            return module.get( "exports" );
        }
        catch ( final Exception e )
        {
            throw ErrorHelper.INSTANCE.handleError( e );
        }
    }

    private ScriptObjectMirror doExecute( final Bindings bindings, final Resource script )
    {
        try
        {
            final String text = IOHelper.readString( script.getBytes() );
            final String source = PRE_SCRIPT + text + POST_SCRIPT;
            return (ScriptObjectMirror) this.engine.eval( source, bindings );
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

    private void runDisposers()
    {
        this.disposers.values().forEach( Runnable::run );
    }

    public void dispose()
    {
        runDisposers();
    }

    @Override
    public NashornRuntime getNashornRuntime()
    {
        return this.nashornRuntime;
    }
}
