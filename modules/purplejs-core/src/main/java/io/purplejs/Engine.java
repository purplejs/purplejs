package io.purplejs;

import java.util.function.Function;

import org.purplejs.engine.ScriptSettings;
import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptExports;

public interface Engine
{
    ScriptSettings getSettings();

    ScriptExports require( ResourcePath resource );

    <R> R execute( ResourcePath resource, Function<ScriptExports, R> command );

    void dispose();
}
