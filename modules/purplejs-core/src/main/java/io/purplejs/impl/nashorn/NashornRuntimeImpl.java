package io.purplejs.impl.nashorn;

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

    public NashornRuntimeImpl( final ScriptEngine engine )
    {
        this.engine = engine;

        final Bindings bindings = this.engine.getBindings( ScriptContext.ENGINE_SCOPE );
        this.arrayProto = (ScriptObjectMirror) bindings.get( "Array" );
        this.objectProto = (ScriptObjectMirror) bindings.get( "Object" );
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
}
