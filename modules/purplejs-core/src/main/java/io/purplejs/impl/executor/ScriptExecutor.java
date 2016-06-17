package io.purplejs.impl.executor;

import java.util.function.Function;

import io.purplejs.ScriptSettings;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;
import io.purplejs.value.ScriptValue;

public interface ScriptExecutor
{
    ScriptSettings getSettings();

    Object executeRequire( ResourcePath path );

    <R> R executeCommand( ScriptExports exports, Function<ScriptExports, R> command );

    ScriptValue newScriptValue( Object value );

    void registerMock( ResourcePath path, Object value );

    void registerFinalizer( ResourcePath path, Runnable callback );
}
