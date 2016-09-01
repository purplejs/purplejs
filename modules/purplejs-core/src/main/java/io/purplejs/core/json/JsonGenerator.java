package io.purplejs.core.json;

/**
 * This interface defines a generator that produces json-like structures.
 */
public interface JsonGenerator
{
    /**
     * Creates a map and adds it.
     *
     * @return instance of this generator.
     */
    JsonGenerator map();

    /**
     * Creates a map and adds it to the parent map.
     *
     * @param key Key for parent-property.
     * @return instance of this generator.
     */
    JsonGenerator map( String key );

    /**
     * Creates an array and adds it.
     *
     * @return instance of this generator.
     */
    JsonGenerator array();

    /**
     * Creates an array and adds it to the parent map.
     *
     * @param key Key for parent-property.
     * @return instance of this generator.
     */
    JsonGenerator array( String key );

    /**
     * Adds a value to the parent.
     *
     * @param value Value to add.
     * @return instance of this generator.
     */
    JsonGenerator value( Object value );

    /**
     * Adds a value to the parent map.
     *
     * @param key   Key for parent-property.
     * @param value Value to add.
     * @return instance of this generator.
     */
    JsonGenerator value( String key, Object value );

    /**
     * End a map or array.
     *
     * @return instance of this generator.
     */
    JsonGenerator end();
}
