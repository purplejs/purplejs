package io.purplejs.impl.executor;

import java.util.Map;
import java.util.function.Function;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.SimpleBindings;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

import io.purplejs.Environment;
import io.purplejs.impl.cache.ScriptExportsCache;
import io.purplejs.impl.util.NashornHelper;
import io.purplejs.impl.value.ScriptValueFactory;
import io.purplejs.impl.value.ScriptValueFactoryImpl;
import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptValue;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public final class ScriptExecutorImpl
    implements ScriptExecutor
{
    // private final ThreadLocal<Object> currentCommand;

    private ScriptEngine engine;

    private Bindings global;

    private Environment environment;

    private ScriptExportsCache exportsCache;

    private Map<ResourcePath, Object> mocks;

    private Map<ResourcePath, Runnable> disposers;

    private ScriptValueFactory scriptValueFactory;

    /*
    public ScriptExecutorImpl()
    {
        this.currentCommand = new ThreadLocal<>();
    }
    */

    @Override
    public Environment getEnvironment()
    {
        return this.environment;
    }

    public void setEngine( final ScriptEngine engine )
    {
        this.engine = engine;
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
        this.global = this.engine.createBindings();
        new CallFunction().register( this.global );
        this.scriptValueFactory = new ScriptValueFactoryImpl( this::invokeMethod );
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

        final ScriptContextImpl context = new ScriptContextImpl( path );
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
        return this.scriptValueFactory.newValue( value );
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
    public void registerDisposer( final ResourcePath path, final Runnable callback )
    {
        this.disposers.put( path, callback );
    }

    private void runDisposer( final ResourcePath path )
    {
        runDisposer( path, this.disposers.get( path ) );
    }

    private void runDisposer( final ResourcePath path, final Runnable callback )
    {
        if ( callback != null )
        {
            callback.run();
        }
    }

    public void dispose()
    {
        this.disposers.forEach( this::runDisposer );
    }

    /*
    @Override
    public <R> R executeCommand( final ScriptExports exports, final Function<ScriptExports, R> command )
    {
        this.currentCommand.set( command );

        try
        {
            return command.apply( exports );
        }
        finally
        {
            this.currentCommand.remove();
        }
    }
    */

    /*
    private Object getCurrentCommand()
    {
        return this.currentCommand.get();
    }
    */
}
