package io.purplejs.core;

import java.io.File;

import io.purplejs.core.internal.EngineBuilderImpl;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourceResolver;
import io.purplejs.core.settings.Settings;

/**
 * This is a builder of engines. It's the main entry point for developers wanted to use
 * the engine directly.
 */
public interface EngineBuilder
{
    /**
     * Sets where the script sources exists. It's only used in the run-mode is set to DEV.
     * This method can be called multiple times if it exists more than one source directory.
     *
     * @param dir directory for the sources.
     * @return the instance of this builder.
     */
    EngineBuilder devSourceDir( File dir );

    /**
     * Sets the classloader to use for creating new instances in the script engine. If not
     * set the, it's using the system application classloader.
     *
     * @param classLoader classloader to use.
     * @return the instance of this builder.
     */
    EngineBuilder classLoader( ClassLoader classLoader );

    /**
     * Sets the resource-loader to use. If not set, it will be set to use a resource-loader that
     * find resources in classpath based on the classloader.
     *
     * @param resourceLoader resource-loader to use.
     * @return the instance of this builder.
     */
    EngineBuilder resourceLoader( ResourceLoader resourceLoader );

    /**
     * Sets the resource-resolver to use. If not set, it will use a default resolver.
     *
     * @param resourceResolver resource-resolver to use.
     * @return the instance of this builder.
     */
    EngineBuilder resourceResolver( ResourceResolver resourceResolver );

    // EngineBuilder beanInjector(BeanInjector beanInjector)

    /**
     * Adds a new engine-module to do more configurations.
     *
     * @param module engine-module to add.
     * @return the instance of this builder.
     */
    EngineBuilder module( EngineModule module );

    /**
     * Add settings to this engine.
     *
     * @param settings settings object to add.
     * @return the instance of this builder.
     */
    EngineBuilder settings( Settings settings );

    /**
     * Builds a new instance of engine. It will also run all initializes configured.
     *
     * @return a new engine instance.
     */
    Engine build();

    /**
     * Returns a new instance of this engine builder.
     *
     * @return a new instance of this builder.
     */
    static EngineBuilder newBuilder()
    {
        return new EngineBuilderImpl();
    }
}
