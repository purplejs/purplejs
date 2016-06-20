package io.purplejs;

import java.util.function.Function;

import com.google.common.util.concurrent.Service;

import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

public interface Engine
{
    ScriptSettings getSettings();

    ScriptExports require( ResourcePath resource );

    <R> R execute( ResourcePath resource, Function<ScriptExports, R> command );

    void dispose();
}

