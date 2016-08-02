package io.purplejs.impl.nashorn;

import java.util.Date;

import javax.script.ScriptEngine;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public interface NashornRuntime
{
    ScriptEngine getEngine();

    ScriptObjectMirror newJsObject();

    ScriptObjectMirror newJsArray();

    String toJsonString( Object value );

    ScriptObjectMirror toJsDate( Date date );
}
