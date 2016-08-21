package io.purplejs.core;

import java.util.Map;

import io.purplejs.core.registry.Registry;
import io.purplejs.core.resource.ResourceLoader;

/**
 * The environment holds all engine configurations that was used to build the
 * engine.
 */
public interface Environment
    extends Registry
{
    /**
     * Returns the configured resource-loader instance.
     *
     * @return the resource-loader instance.
     */
    ResourceLoader getResourceLoader();

    /**
     * Returns the configured classloader.
     *
     * @return the classloader to use.
     */
    ClassLoader getClassLoader();

    /**
     * Returns the user-defined application configurations as a map.
     *
     * @return the application configuration.
     */
    Map<String, String> getConfig();
}
