package io.purplejs;

import java.util.Map;

import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptValue;

public interface ExecutionContext
{
    ResourcePath getResource();

    ResourceLoader getLoader();

    Object getCommand();

    // <T> Supplier<T> lookup( Class<T> type );

    // Registry getRegistry();

    void finalizer( Runnable runnable );

    ScriptSettings getSettings();

    Object newBean( String type );

    Object require( String path );

    ResourcePath resolve( String path );

    ScriptValue toScriptValue( Object value );

    Object toNativeObject( Object value );

    Map<String, String> getConfig();

    void registerMock( String path, Object value );
}
