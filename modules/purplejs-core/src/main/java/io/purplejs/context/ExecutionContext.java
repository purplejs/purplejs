package io.purplejs.context;

import java.util.Optional;
import java.util.function.Supplier;

import io.purplejs.Environment;
import io.purplejs.resource.ResourcePath;
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

    Optional<?> getOptional( String type );

    <T> T newBean( Class<T> type );

    Object newBean( String type );
}
