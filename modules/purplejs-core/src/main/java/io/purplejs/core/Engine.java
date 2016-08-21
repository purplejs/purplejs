package io.purplejs.core;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;

/**
 * This is the Engine and is used for executing the javascript code.
 */
public interface Engine
    extends Environment
{
    /**
     * Executes a javascript resource and returns the exported entries (if any).
     *
     * @param resource resource path for the javascript.
     * @return script exports for the executed file.
     */
    ScriptExports require( ResourcePath resource );

    /**
     * Disposes of all engine resources and executes all registered disposers.
     */
    void dispose();
}
