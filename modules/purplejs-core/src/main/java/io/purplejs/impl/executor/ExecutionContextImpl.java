package io.purplejs.impl.executor;

import java.util.Optional;
import java.util.function.Supplier;

import io.purplejs.Environment;
import io.purplejs.context.ExecutionContext;
import io.purplejs.impl.util.JsObjectConverter;
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
    public Environment getEnvironment()
    {
        return this.environment;
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
    public Supplier<?> getSupplier( final String type )
        throws Exception
    {
        return this.environment.getSupplier( forName( type ) );
    }

    @Override
    public Optional<?> getOptional( final String type )
        throws Exception
    {
        return this.environment.getOptional( forName( type ) );
    }

    @Override
    public <T> T newBean( final Class<T> type )
        throws Exception
    {
        return type.newInstance();
    }

    @Override
    public Object newBean( final String type )
        throws Exception
    {
        return newBean( forName( type ) );
    }
}
