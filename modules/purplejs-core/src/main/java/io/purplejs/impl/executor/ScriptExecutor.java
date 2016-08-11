package io.purplejs.impl.executor;

import io.purplejs.Environment;
import io.purplejs.impl.nashorn.NashornRuntime;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;
import io.purplejs.value.ScriptValue;

public interface ScriptExecutor
{
    Environment getEnvironment();

    ScriptExports executeMain( ResourcePath path );

    Object executeRequire( ResourcePath path );

    NashornRuntime getNashornRuntime();

    ScriptValue newScriptValue( Object value );

    void registerMock( ResourcePath path, Object value );

    void registerDisposer( ResourcePath path, Runnable callback );
}
