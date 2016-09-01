package io.purplejs.core.json;

/**
 * This interface is used to serialize a object to a json-like structure. Used to map
 * Java objects to native javascript objects.
 */
public interface JsonSerializable
{
    /**
     * Serialize this object.
     *
     * @param gen Generator instance to use.
     */
    void serialize( JsonGenerator gen );
}
