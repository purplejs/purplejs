package org.purplejs.impl.runtime;

import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import org.purplejs.resource.ResourcePath;
import org.purplejs.resource.ResourceLoader;
import org.purplejs.runtime.ScriptRuntime;
import org.purplejs.value.ScriptExports;

public final class ScriptRuntimeImpl
    implements ScriptRuntime
{
    private ScriptEngine engine;

    private Map<String, Object> mocks;

    private Bindings global;

    private ScriptExportsCache exportsCache;

    private ResourceLoader resourceResolver;

    private boolean devMode;

    @Override
    public ScriptExports execute( final ResourcePath path )
    {
        return null;
    }
}
