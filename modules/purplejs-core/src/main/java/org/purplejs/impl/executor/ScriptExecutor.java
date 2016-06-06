package org.purplejs.impl.executor;

import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptValue;

public interface ScriptExecutor
{
    ScriptSettings getSettings();

    Object executeRequire( ResourcePath path );

    ScriptValue newScriptValue( Object value );
}
