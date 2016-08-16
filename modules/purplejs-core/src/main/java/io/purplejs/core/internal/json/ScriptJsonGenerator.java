package io.purplejs.core.internal.json;

import io.purplejs.core.internal.nashorn.NashornHelper;
import io.purplejs.core.internal.nashorn.NashornRuntime;

public final class ScriptJsonGenerator
    extends AbstractJsonGenerator
{
    private final NashornRuntime runtime;

    public ScriptJsonGenerator( final NashornRuntime runtime )
    {
        this.runtime = runtime;
    }

    @Override
    protected Object newMap()
    {
        return this.runtime.newJsObject();
    }

    @Override
    protected Object newArray()
    {
        return this.runtime.newJsArray();
    }

    @Override
    protected boolean isMap( final Object value )
    {
        return NashornHelper.INSTANCE.isObjectType( value );
    }

    @Override
    protected boolean isArray( final Object value )
    {
        return NashornHelper.INSTANCE.isArrayType( value );
    }

    @Override
    protected void putInMap( final Object map, final String key, final Object value )
    {
        if ( value != null )
        {
            NashornHelper.INSTANCE.addToObject( map, key, value );
        }
    }

    @Override
    protected void addToArray( final Object array, final Object value )
    {
        NashornHelper.INSTANCE.addToArray( array, value );
    }

    @Override
    protected AbstractJsonGenerator newGenerator()
    {
        return new ScriptJsonGenerator( this.runtime );
    }
}
