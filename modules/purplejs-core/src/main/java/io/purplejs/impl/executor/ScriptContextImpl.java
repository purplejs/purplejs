package io.purplejs.impl.executor;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.SimpleScriptContext;

import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;

final class ScriptContextImpl
    extends SimpleScriptContext
{
    private final ResourcePath resource;

    ScriptContextImpl( final ResourcePath resource )
    {
        this.resource = resource;
    }

    void setEngineScope( final Bindings scope )
    {
        setBindings( scope, ENGINE_SCOPE );
    }

    void setGlobalScope( final Bindings scope )
    {
        setBindings( scope, GLOBAL_SCOPE );
    }

    @Override
    public Object getAttribute( final String name )
    {
        if ( name.equals( ScriptEngine.FILENAME ) )
        {
            return getFileName();
        }

        return super.getAttribute( name );
    }

    private String getFileName()
    {
        return this.resource.toString();
    }
}
