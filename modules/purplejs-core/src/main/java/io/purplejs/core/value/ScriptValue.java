package io.purplejs.core.value;

import java.util.List;
import java.util.Set;

import com.google.gson.JsonElement;

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

    ScriptValue call( Object... args );

    JsonElement toJson();

    Object getRaw();
}
