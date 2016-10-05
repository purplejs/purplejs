package io.purplejs.core.internal.util;

import java.util.List;
import java.util.Map;

import io.purplejs.core.internal.json.ScriptJsonGenerator;
import io.purplejs.core.internal.nashorn.NashornHelper;
import io.purplejs.core.internal.nashorn.NashornRuntime;
import io.purplejs.core.json.JsonSerializable;

public final class JsObjectConverter
{
    private final NashornRuntime runtime;

    public JsObjectConverter( final NashornRuntime runtime )
    {
        this.runtime = runtime;
    }

    public Object toJs( final Object value )
    {
        if ( value instanceof JsonSerializable )
        {
            return toJs( (JsonSerializable) value );
        }

        if ( value instanceof List )
        {
            return toJs( (List) value );
        }

        if ( value instanceof Map )
        {
            return toJs( (Map) value );
        }

        return value;
    }

    private Object toJs( final JsonSerializable value )
    {
        final ScriptJsonGenerator generator = new ScriptJsonGenerator( this.runtime );
        value.serialize( generator );
        return generator.getRoot();
    }

    private Object toJs( final List list )
    {
        final Object array = this.runtime.newJsArray();
        for ( final Object element : list )
        {
            NashornHelper.addToArray( array, toJs( element ) );
        }

        return array;
    }

    private Object toJs( final Map<?, ?> map )
    {
        final Object result = this.runtime.newJsObject();
        for ( final Map.Entry<?, ?> entry : map.entrySet() )
        {
            NashornHelper.addToObject( result, entry.getKey().toString(), toJs( entry.getValue() ) );
        }

        return result;
    }
}
