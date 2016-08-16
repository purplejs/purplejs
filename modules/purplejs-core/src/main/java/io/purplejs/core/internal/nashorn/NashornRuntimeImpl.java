package io.purplejs.core.internal.nashorn;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

final class NashornRuntimeImpl
    implements NashornRuntime
{
    private final ScriptEngine engine;

    private final ScriptObjectMirror arrayProto;

    private final ScriptObjectMirror objectProto;

    private final ScriptObjectMirror jsonProto;

    NashornRuntimeImpl( final ScriptEngine engine )
    {
        this.engine = engine;

        final Bindings bindings = this.engine.getBindings( ScriptContext.ENGINE_SCOPE );
        this.arrayProto = (ScriptObjectMirror) bindings.get( "Array" );
        this.objectProto = (ScriptObjectMirror) bindings.get( "Object" );
        this.jsonProto = (ScriptObjectMirror) bindings.get( "JSON" );
    }

    @Override
    public ScriptEngine getEngine()
    {
        return this.engine;
    }

    @Override
    public ScriptObjectMirror newJsObject()
    {
        return (ScriptObjectMirror) this.objectProto.newObject();
    }

    @Override
    public ScriptObjectMirror newJsArray()
    {
        return (ScriptObjectMirror) this.arrayProto.newObject();
    }

    @Override
    public String toJsonString( final Object value )
    {
        return (String) this.jsonProto.callMember( "stringify", value );
    }

    @Override
    public ScriptObjectMirror parseJson( final String value )
    {
        return (ScriptObjectMirror) this.jsonProto.callMember( "parse", value );
    }
}
