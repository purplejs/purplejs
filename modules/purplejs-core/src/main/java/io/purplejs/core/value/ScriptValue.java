package io.purplejs.core.value;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;

/**
 * This interface defines a script value. It's a wrapper around Nashorn's own objects.
 */
public interface ScriptValue
{
    /**
     * Returns true if this value is an array.
     *
     * @return true if array.
     */
    boolean isArray();

    /**
     * Returns true if this value is a object.
     *
     * @return true if object.
     */
    boolean isObject();

    /**
     * Returns true if this value is a simple value.
     *
     * @return true if value.
     */
    boolean isValue();

    /**
     * Returns true if this value is a function.
     *
     * @return true if function.
     */
    boolean isFunction();

    /**
     * Returns the value if it's type value.
     *
     * @return the value.
     */
    Object getValue();

    /**
     * Returns the value if the type is value and converts it.
     *
     * @param type Type to convert to.
     * @param <T>  Type to convert to.
     * @return the value
     */
    <T> T getValue( Class<T> type );

    /**
     * Returns the object keys if this value is object.
     *
     * @return set of object keys.
     */
    Set<String> getKeys();

    /**
     * Returns true if it has a member by specified name.
     *
     * @param key Name of member.
     * @return true if it has the named member.
     */
    boolean hasMember( String key );

    /**
     * Returns the named member or null.
     *
     * @param key Name of member.
     * @return named member or null.
     */
    ScriptValue getMember( String key );

    /**
     * Returns the value as array.
     *
     * @return value as array.
     */
    List<ScriptValue> getArray();

    /**
     * Returns the value as map.
     *
     * @return value as map.
     */
    Map<String, ScriptValue> getMap();

    /**
     * Call this function if it's a function.
     *
     * @param args Optional arguments.
     * @return result of function call.
     */
    ScriptValue call( Object... args );

    /**
     * Returns the value as JSON.
     *
     * @return value as JSON.
     */
    JsonElement toJson();

    /**
     * Returns the value as Java object. For simple values, it will return the value. For objects it will return a
     * map of objects. For arrays it will return a list of objects and for functions it will return a function object.
     *
     * @return the value.
     */
    Object toJavaObject();

    /**
     * Returns the raw (wrapped) object.
     *
     * @return the raw object.
     */
    Object getRaw();
}
