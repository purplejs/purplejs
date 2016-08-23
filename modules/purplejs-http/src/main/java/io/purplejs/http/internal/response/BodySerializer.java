package io.purplejs.http.internal.response;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.value.ScriptValue;

final class BodySerializer
{
    private final static MediaType JSON_TYPE = MediaType.create( "application", "json" );

    ByteSource toBody( final Object value )
    {
        if ( value == null )
        {
            return null;
        }

        if ( value instanceof Resource )
        {
            return ( (Resource) value ).getBytes();
        }

        if ( value instanceof ByteSource )
        {
            return (ByteSource) value;
        }

        if ( value instanceof byte[] )
        {
            return ByteSource.wrap( (byte[]) value );
        }

        if ( value instanceof ScriptValue )
        {
            return toBody( (ScriptValue) value );
        }

        return toBody( value.toString() );
    }

    private ByteSource toBody( final ScriptValue value )
    {
        if ( value.isFunction() )
        {
            return toBody( value.call() );
        }

        if ( value.isArray() )
        {
            return toBody( value.toJson() );
        }

        if ( value.isObject() )
        {
            return toBody( value.toJson() );
        }

        return toBody( value.getValue() );
    }

    private ByteSource toBody( final String value )
    {
        return ByteSource.wrap( value.getBytes( Charsets.UTF_8 ) );
    }

    MediaType findType( final ScriptValue value )
    {
        if ( value == null )
        {
            return null;
        }

        if ( value.isArray() || value.isObject() )
        {
            return JSON_TYPE;
        }

        return null;
    }
}
