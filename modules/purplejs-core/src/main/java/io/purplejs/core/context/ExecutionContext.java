package io.purplejs.core.context;

import java.util.Optional;
import java.util.function.Supplier;

import io.purplejs.core.Engine;
import io.purplejs.core.Environment;
import io.purplejs.core.registry.Registry;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptValue;

public interface ExecutionContext
{
    Engine getEngine();

    ResourcePath getResource();

    Environment getEnvironment();

    ScriptLogger getLogger();

    Registry getRegistry();

    Object require( String path );

    ResourcePath resolve( String path );

    void disposer( Runnable runnable );

    ScriptValue toScriptValue( Object value );

    Object toNativeObject( Object value );

    void registerMock( String path, Object value );

    Object getInstance( String type )
        throws Exception;

    Supplier<?> getProvider( String type )
        throws Exception;

    Optional<?> getOptional( String type )
        throws Exception;

    <T> T newBean( Class<T> type )
        throws Exception;

    Object newBean( String type )
        throws Exception;
}
