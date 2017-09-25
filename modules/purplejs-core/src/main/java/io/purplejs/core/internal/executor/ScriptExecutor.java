package io.purplejs.core.internal.executor;

import io.purplejs.core.Environment;
import io.purplejs.core.internal.nashorn.NashornRuntime;
import io.purplejs.core.internal.resolver.ResourcePathResolver;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;
import io.purplejs.core.value.ScriptValue;

public interface ScriptExecutor
{
    Environment getEnvironment();

    ScriptExports executeMain( ResourcePath path );

    Object executeRequire( ResourcePath path );

    NashornRuntime getNashornRuntime();

    ScriptValue newScriptValue( Object value );

    void registerMock( ResourcePath path, Object value );

    void registerDisposer( ResourcePath path, Runnable callback );

    ResourcePathResolver getRequirePathResolver();

    ResourcePathResolver getStandardPathResolver();
}
