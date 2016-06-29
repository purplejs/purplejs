package io.purplejs.v2;

import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

// EngineAware
public interface Engine
    extends Environment
{
    ScriptExports require( ResourcePath resource );

    void dispose();
}
