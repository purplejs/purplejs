package io.purplejs.core.internal.executor;

import io.purplejs.core.Engine;
import io.purplejs.core.Environment;
import io.purplejs.core.internal.util.JsObjectConverter;
import io.purplejs.core.internal.util.ResourceHelper;
import io.purplejs.core.resource.ResourcePath;

final class GlobalExecutionContextImpl
    implements GlobalExecutionContext
{
    private final ScriptExecutor executor;

    private final Environment environment;

    private final JsObjectConverter converter;

    GlobalExecutionContextImpl( final ScriptExecutor executor )
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
    public Object toNativeObject( final Object value )
    {
        return this.converter.toJs( value );
    }

    @Override
    public void registerMock( final String path, final Object value )
    {
        this.executor.registerMock( ResourcePath.from( path ), value );
    }

    @Override
    public void disposer( final Runnable runnable )
    {
        this.executor.registerDisposer( ResourcePath.from( "/" ), runnable );
    }

    private Class<?> forName( final String type )
        throws Exception
    {
        return Class.forName( type, true, this.environment.getClassLoader() );
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
}
