package io.purplejs.core.internal.executor;

import io.purplejs.core.Engine;
import io.purplejs.core.Environment;
import io.purplejs.core.resource.ResourcePath;

/**
 * This object is available as a global javascript object (__ - double underscore).
 */
public interface GlobalExecutionContext
{
    /**
     * Returns the engine instance.
     *
     * @return engine instance.
     */
    Engine getEngine();

    /**
     * Returns the script environment.
     *
     * @return script environment.
     */
    Environment getEnvironment();

    /**
     * Converts a object to native javascript object.
     *
     * @param value Value to convert.
     * @return a native javascript object.
     */
    Object toNativeObject( Object value );

    /**
     * Register a mock object to the specified path.
     *
     * @param path  Absolute path to the object.
     * @param value Object to return from require.
     */
    void registerMock( String path, Object value );

    /**
     * Adds a new disposer.
     *
     * @param runnable Disposer to add.
     */
    void disposer( Runnable runnable );

    /**
     * Constructs a new instance of the specified type.
     *
     * @param type Type of object (class-name).
     * @return the new instance.
     * @throws Exception if anything went wrong.
     */
    Object newBean( String type )
        throws Exception;

    ResourcePath getCurrentScript();

    ResourcePath getCallingScript();
}
