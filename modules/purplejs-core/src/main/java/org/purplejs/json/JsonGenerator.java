package org.purplejs.json;

public interface JsonGenerator
{
    JsonGenerator object();

    JsonGenerator object( String key );

    JsonGenerator array();

    JsonGenerator array( String key );

    JsonGenerator value( Object value );

    JsonGenerator value( String key, Object value );

    JsonGenerator end();
}
