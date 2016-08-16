package io.purplejs.core.internal.nashorn;

import javax.script.ScriptEngine;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public interface NashornRuntime
{
    ScriptEngine getEngine();

    ScriptObjectMirror newJsObject();

    ScriptObjectMirror newJsArray();

    String toJsonString( Object value );
}
