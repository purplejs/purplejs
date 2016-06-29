package io.purplejs;

import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

public interface Engine
    extends Environment
{
    ScriptExports require( ResourcePath resource );

    // <R> R execute( ResourcePath resource, Function<ScriptExports, R> command );

    void dispose();
}
