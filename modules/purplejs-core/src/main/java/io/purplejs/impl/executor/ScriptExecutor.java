package io.purplejs.impl.executor;

import io.purplejs.Environment;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptValue;

public interface ScriptExecutor
{
    Environment getEnvironment();

    Object executeRequire( ResourcePath path );

    // <R> R executeCommand( ScriptExports exports, Function<ScriptExports, R> command );

    ScriptValue newScriptValue( Object value );

    void registerMock( ResourcePath path, Object value );

    void registerDisposer( ResourcePath path, Runnable callback );
}
