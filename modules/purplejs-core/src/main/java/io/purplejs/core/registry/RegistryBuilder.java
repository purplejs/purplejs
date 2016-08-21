package io.purplejs.core.registry;

import java.util.function.Supplier;

/**
 * This is a builder of registries. It's possible to either bind an instance or a
 * provider of instances to the registry.
 */
public interface RegistryBuilder
{
    /**
     * Binds an instance to a type.
     *
     * @param type     type to bind.
     * @param instance instance of the type.
     * @param <T>      type of instance.
     * @return the instance of this builder.
     */
    <T> RegistryBuilder instance( Class<T> type, T instance );

    /**
     * Binds a provider of a type. Can be used for non-singleton services or values.
     *
     * @param type     type to bind.
     * @param supplier the supplier (or factory) to create an instance of type.
     * @param <T>      type of instance.
     * @return the instance of this builder.
     */
    <T> RegistryBuilder provider( Class<T> type, Supplier<T> supplier );

    /**
     * Build the registry based on the user configuration.
     *
     * @return a newly created registry instance.
     */
    Registry build();

    /**
     * Creates a new builder of this type.
     *
     * @return a new builder instance.
     */
    static RegistryBuilder newBuilder()
    {
        return new RegistryBuilderImpl();
    }
}
