package org.purplejs.impl.executor;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.SimpleScriptContext;

import org.purplejs.resource.Resource;

final class ScriptContextImpl
    extends SimpleScriptContext
{
    private final Resource resource;

    public ScriptContextImpl( final Resource resource )
    {
        this.resource = resource;
    }

    public void setEngineScope( final Bindings scope )
    {
        setBindings( scope, ENGINE_SCOPE );
    }

    public void setGlobalScope( final Bindings scope )
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
        return this.resource.getPath().toString();
    }
}
