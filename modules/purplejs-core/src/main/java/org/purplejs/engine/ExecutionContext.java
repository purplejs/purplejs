package org.purplejs.engine;

import java.util.Map;

import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptValue;

public interface ExecutionContext
{
    ResourcePath getResource();

    ResourceLoader getLoader();

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
