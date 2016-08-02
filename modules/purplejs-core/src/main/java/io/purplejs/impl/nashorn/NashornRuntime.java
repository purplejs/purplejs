package io.purplejs.impl.nashorn;

import javax.script.ScriptEngine;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public interface NashornRuntime
{
    ScriptEngine getEngine();

    ScriptObjectMirror newJsObject();

    ScriptObjectMirror newJsArray();
}
