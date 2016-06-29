package io.purplejs.v3;

import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

public interface Engine
    extends Environment
{
    ScriptExports require( ResourcePath resource );

    void dispose();
}
