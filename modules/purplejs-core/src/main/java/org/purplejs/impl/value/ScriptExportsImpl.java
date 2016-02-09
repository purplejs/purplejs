package org.purplejs.impl.value;

import org.purplejs.value.ScriptExports;
import org.purplejs.value.ScriptValue;

public final class ScriptExportsImpl
    implements ScriptExports
{
    private final ScriptValue value;

    public ScriptExportsImpl( final ScriptValue value )
    {
        this.value = value;
    }

    @Override
    public ScriptValue getValue()
    {
        return this.value;
    }

    @Override
    public boolean hasMethod( final String name )
    {
        return getMethod( name ) != null;
    }

    @Override
    public ScriptValue executeMethod( final String name, final Object... args )
    {
        final ScriptValue method = getMethod( name );
        if ( method == null )
        {
            return null;
        }

        return method.call( args );
    }

    private ScriptValue getMethod( final String name )
    {
        final ScriptValue func = this.value.getMember( name );
        return ( ( func != null ) && func.isFunction() ) ? func : null;
    }
}
