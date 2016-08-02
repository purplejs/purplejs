package io.purplejs;

import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

public interface Engine
    extends Environment
{
    ScriptExports require( ResourcePath resource );

    // ScriptValue execute( ResourcePath resource );

    // ScriptExports require( Resource resource );
    // ScriptValue execute( Resource resource );

    void dispose();
}
