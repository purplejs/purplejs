package io.purplejs.core;

/**
 * Engine module is used for configure the engine. If a library needs to bind some
 * instances to the engine, it's done using a engine-module.
 *
 * A set of engine-module's can be auto-discovered via standard Java service-loader
 * pattern. Just add the class-name to META-INF/services/io.purplejs.core.EngineModule.
 */
public interface EngineModule
{
    /**
     * Do the configuration by using the provided binder.
     *
     * @param binder binder instance to use.
     */
    void configure( EngineBinder binder );
}
