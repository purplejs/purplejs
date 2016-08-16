package io.purplejs.core;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;

public interface Engine
    extends Environment
{
    ScriptExports require( ResourcePath resource );

    void dispose();
}
