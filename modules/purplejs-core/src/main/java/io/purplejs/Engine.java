package io.purplejs;

import java.util.function.Function;

import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

public interface Engine
    extends ScriptSettings
{
    ScriptExports require( ResourcePath resource );

    <R> R execute( ResourcePath resource, Function<ScriptExports, R> command );

    void dispose();
}
