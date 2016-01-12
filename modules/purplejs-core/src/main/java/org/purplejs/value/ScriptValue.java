package org.purplejs.value;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ScriptValue
{
    boolean isArray();

    boolean isObject();

    boolean isValue();

    boolean isFunction();

    Object getValue();

    <T> T getValue( Class<T> type );

    Set<String> getKeys();

    boolean hasMember( String key );

    ScriptValue getMember( String key );

    List<ScriptValue> getArray();

    <T> List<T> getArray( Class<T> type );

    Map<String, Object> getMap();

    ScriptValue call( Object... args );
}
