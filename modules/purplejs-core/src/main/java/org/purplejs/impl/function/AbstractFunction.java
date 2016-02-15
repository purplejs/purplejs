package org.purplejs.impl.function;

import javax.script.Bindings;

import jdk.nashorn.api.scripting.AbstractJSObject;

public abstract class AbstractFunction
    extends AbstractJSObject
{
    private final String name;

    public AbstractFunction( final String name )
    {
        this.name = name;
    }

    @Override
    public final boolean isFunction()
    {
        return true;
    }

    @Override
    public final boolean isStrictFunction()
    {
        return true;
    }

    public final void register( final Bindings bindings )
    {
        bindings.put( this.name, this );
    }
}
