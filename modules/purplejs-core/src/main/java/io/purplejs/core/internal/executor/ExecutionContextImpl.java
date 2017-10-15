package io.purplejs.core.internal.executor;

import java.util.function.Supplier;

import io.purplejs.core.Engine;
import io.purplejs.core.Environment;
import io.purplejs.core.internal.util.JsObjectConverter;
import io.purplejs.core.registry.Registry;
import io.purplejs.core.resource.ResourceHelper;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptValue;

final class ExecutionContextImpl
    implements ExecutionContext
{
    private final ScriptExecutor executor;

    private final Environment environment;

    private final JsObjectConverter converter;

    ExecutionContextImpl( final ScriptExecutor executor )
    {
        this.executor = executor;
        this.environment = this.executor.getEnvironment();
        this.converter = new JsObjectConverter( this.executor.getNashornRuntime() );
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
    public void disposer( final Runnable runnable )
    {
        this.executor.registerDisposer( ResourcePath.from( "/" ), runnable );
    }

    @Override
    public Object require( final ResourcePath path )
    {
        return this.executor.executeRequire( path );
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

    @Override
    public ResourcePath getCurrentScript()
    {
        return ResourceHelper.getCurrentScript();
    }

    @Override
    public ResourcePath getCallingScript()
    {
        return ResourceHelper.getCallingScript();
    }

    @Override
    public ResourcePath resolve( final String path )
    {
        return resolve( getCurrentScript(), path );
    }

    @Override
    public ResourcePath resolve( final ResourcePath base, final String path )
    {
        final ResourcePath parent = base.getParent();
        return parent != null ? parent.resolve( path ) : ResourcePath.ROOT.resolve( path );
    }

    @Override
    public ResourceLoader getResourceLoader()
    {
        return getEnvironment().getResourceLoader();
    }
}
