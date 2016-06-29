package io.purplejs.v3.context;

import java.util.function.Supplier;

import io.purplejs.resource.ResourcePath;
import io.purplejs.v3.Environment;
import io.purplejs.value.ScriptValue;

public interface ExecutionContext
    extends Environment
{
    ResourcePath getResource();

    void disposer( Runnable runnable );

    Object require( String path );

    ResourcePath resolve( String path );

    ScriptValue toScriptValue( Object value );

    Object toNativeObject( Object value );

    void registerMock( String path, Object value );

    Object getInstance( String type );

    Supplier<?> getSupplier( String type );

    Object newInstance( String type );
}
