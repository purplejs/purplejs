package io.purplejs.impl.executor;

import io.purplejs.ScriptSettings;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptValue;

public interface ScriptExecutor
{
    ScriptSettings getSettings();

    Object executeRequire( ResourcePath path );

    ScriptValue newScriptValue( Object value );

    void registerMock( ResourcePath path, Object value );

    void registerFinalizer( ResourcePath path, Runnable callback );
}
