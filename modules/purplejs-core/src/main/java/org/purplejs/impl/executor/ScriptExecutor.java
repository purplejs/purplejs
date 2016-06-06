package org.purplejs.impl.executor;

import org.purplejs.engine.ScriptSettings;
import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptValue;

public interface ScriptExecutor
{
    ScriptSettings getSettings();

    Object executeRequire( ResourcePath path );

    ScriptValue newScriptValue( Object value );

    void registerMock( ResourcePath path, Object value );

    void registerFinalizer( ResourcePath path, Runnable callback );
}
