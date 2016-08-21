package io.purplejs.core;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This class is used to bind configuration values to the engine. It is used by the
 * EngineModule.
 */
public interface EngineBinder
{
    /**
     * Binds an instance to a type.
     *
     * @param type     type to bind.
     * @param instance instance of the type.
     * @param <T>      type of instance.
     * @return the instance of this binder.
     */
    <T> EngineBinder instance( Class<T> type, T instance );

    /**
     * Binds a provider of a type. Can be used for non-singleton services or values.
     *
     * @param type     type to bind.
     * @param supplier the supplier (or factory) to create an instance of type.
     * @param <T>      type of instance.
     * @return the instance of this binder.
     */
    <T> EngineBinder provider( Class<T> type, Supplier<T> supplier );

    /**
     * Adds a global variable to the script engine.
     *
     * @param name  name of variable.
     * @param value variable instance.
     * @return the instance of this binder.
     */
    EngineBinder globalVariable( String name, Object value );

    /**
     * Adds a configuration value to the application configuration.
     *
     * @param name  name of setting.
     * @param value setting value.
     * @return the instance of this binder.
     */
    EngineBinder config( String name, String value );

    /**
     * Binds an initializer that is executed when the engine is created.
     *
     * @param initializer initializer to bind.
     * @return the instance of this binder.
     */
    EngineBinder initializer( Consumer<Engine> initializer );

    /**
     * Binds a disposer that is executed when the engine is disposed or if the
     * script cache is expired.
     *
     * @param disposer disposer to bind.
     * @return the instance of this binder.
     */
    EngineBinder disposer( Consumer<Engine> disposer );
}
