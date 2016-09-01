package io.purplejs.core.value;

import io.purplejs.core.resource.ResourcePath;

/**
 * This interface defines a set of exported methods from javascript.
 */
public interface ScriptExports
{
    /**
     * Resource path for the script that exported the methods.
     *
     * @return resource path for the script.
     */
    ResourcePath getResource();

    /**
     * Exported object as a value.
     *
     * @return exported object as a value.
     */
    ScriptValue getValue();

    /**
     * Returns true if it has a method by the specified name, false otherwise.
     *
     * @param name Name of the method.
     * @return true if the method exists.
     */
    boolean hasMethod( String name );

    /**
     * Executes a method with optional arguments.
     *
     * @param name Name of the method.
     * @param args Optional arguments.
     * @return a return value if anything.
     */
    ScriptValue executeMethod( String name, Object... args );
}
