package io.purplejs.core;

import java.io.File;
import java.util.List;

import io.purplejs.core.inject.BeanInjector;
import io.purplejs.core.registry.Registry;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourceResolver;
import io.purplejs.core.settings.Settings;

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
     * Returns the configured resource-resolver instance.
     *
     * @return the resource-resolver instance.
     */
    ResourceResolver getResourceResolver();

    /**
     * Returns the configured bean-injector instance.
     *
     * @return the bean-injector instance.
     */
    BeanInjector getBeanInjector();

    /**
     * Returns the configured classloader.
     *
     * @return the classloader to use.
     */
    ClassLoader getClassLoader();

    /**
     * Returns a list of all development source directories. It's only used when RunMode is DEV.
     *
     * @return a list of all development source directories.
     */
    List<File> getDevSourceDirs();

    /**
     * Returns the settings object.
     *
     * @return settings object.
     */
    Settings getSettings();
}
