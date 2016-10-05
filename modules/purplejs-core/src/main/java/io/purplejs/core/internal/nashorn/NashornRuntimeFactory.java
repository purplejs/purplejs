package io.purplejs.core.internal.nashorn;

import javax.script.ScriptEngine;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

public final class NashornRuntimeFactory
{
    private final static String[] ENGINE_ARGS = {"--global-per-engine", "-strict", "--language=es6"};

    private final NashornScriptEngineFactory scriptEngineFactory;

    public NashornRuntimeFactory()
    {
        this.scriptEngineFactory = new NashornScriptEngineFactory();
    }

    public NashornRuntime newRuntime( final ClassLoader loader )
    {
        final ScriptEngine engine = this.scriptEngineFactory.getScriptEngine( ENGINE_ARGS, loader );
        return new NashornRuntimeImpl( engine );
    }
}
