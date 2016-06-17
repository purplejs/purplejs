package io.purplejs.impl.json;

import io.purplejs.impl.util.NashornHelper;

public final class ScriptJsonGenerator
    extends AbstractJsonGenerator
{
    @Override
    protected Object newMap()
    {
        return NashornHelper.newNativeObject();
    }

    @Override
    protected Object newArray()
    {
        return NashornHelper.newNativeArray();
    }

    @Override
    protected boolean isMap( final Object value )
    {
        return NashornHelper.isNativeObject( value );
    }

    @Override
    protected boolean isArray( final Object value )
    {
        return NashornHelper.isNativeArray( value );
    }

    @Override
    protected void putInMap( final Object map, final String key, final Object value )
    {
        if ( value != null )
        {
            NashornHelper.addToNativeObject( map, key, value );
        }
    }

    @Override
    protected void addToArray( final Object array, final Object value )
    {
        NashornHelper.addToNativeArray( array, value );
    }

    @Override
    protected AbstractJsonGenerator newGenerator()
    {
        return new ScriptJsonGenerator();
    }
}
