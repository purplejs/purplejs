package io.purplejs.impl.nashorn;

import javax.script.ScriptEngine;

public final class NashornRuntimeFactory
{
    public NashornRuntime newRuntime( final ClassLoader classLoader )
    {
        final ScriptEngine engine = NashornHelper.newScriptEngine( classLoader );
        return new NashornRuntimeImpl( engine );
    }
}
