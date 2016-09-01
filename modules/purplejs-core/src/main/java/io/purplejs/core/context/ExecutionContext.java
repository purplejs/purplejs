package io.purplejs.core.context;

import java.util.function.Supplier;

import io.purplejs.core.Engine;
import io.purplejs.core.Environment;
import io.purplejs.core.registry.Registry;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptValue;

/**
 * This object is available as a global javascript object (__ - double underscore).
 */
public interface ExecutionContext
{
    /**
     * Returns the engine instance.
     *
     * @return engine instance.
     */
    Engine getEngine();

    /**
     * Returns the actual script running.
     *
     * @return script running.
     */
    ResourcePath getResource();

    /**
     * Returns the script environment.
     *
     * @return script environment.
     */
    Environment getEnvironment();

    /**
     * Returns the script logger.
     *
     * @return script logger.
     */
    ScriptLogger getLogger();

    /**
     * Returns the component registry.
     *
     * @return the component registry.
     */
    Registry getRegistry();

    /**
     * Require a new javascript file or json file relative to the current file.
     *
     * @param path Absolute or relative path.
     * @return exports for javascript or json.
     */
    Object require( String path );

    /**
     * Resolve a new file resource. The file can be relative or absolute.
     *
     * @param path Absolute or relative path.
     * @return the resource path.
     */
    ResourcePath resolve( String path );

    /**
     * Adds a new disposer.
     *
     * @param runnable Disposer to add.
     */
    void disposer( Runnable runnable );

    /**
     * Converts a value to a script value.
     *
     * @param value Value to convert.
     * @return converted script value instance.
     */
    ScriptValue toScriptValue( Object value );

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
     * Returns an instance of the specified component from the registry.
     *
     * @param type Type of object (class-name).
     * @return an instance of the component.
     * @throws Exception if anything went wrong.
     */
    Object getInstance( String type )
        throws Exception;

    /**
     * Returns an instance of the specified component from the registry.
     *
     * @param type Type of object (class-name).
     * @return an instance of the component or null if not exists.
     * @throws Exception if anything went wrong.
     */
    Object getInstanceOrNull( String type )
        throws Exception;

    /**
     * Returns a provider of the specified component from the registry.
     *
     * @param type Type of object (class-name).
     * @return a provider of the component.
     * @throws Exception if anything went wrong.
     */
    Supplier<?> getProvider( String type )
        throws Exception;

    /**
     * Constructs a new instance of the specified type.
     *
     * @param type Type of object (class-name).
     * @return the new instance.
     * @throws Exception if anything went wrong.
     */
    Object newBean( String type )
        throws Exception;
}
