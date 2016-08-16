package io.purplejs.core.internal.value;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;
import io.purplejs.core.value.ScriptValue;

public final class ScriptExportsImpl
    implements ScriptExports
{
    private final ResourcePath resource;

    private final ScriptValue value;

    public ScriptExportsImpl( final ResourcePath resource, final ScriptValue value )
    {
        this.resource = resource;
        this.value = value;
    }

    @Override
    public ResourcePath getResource()
    {
        return this.resource;
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
