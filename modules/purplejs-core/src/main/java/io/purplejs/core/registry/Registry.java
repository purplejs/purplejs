package io.purplejs.core.registry;

import java.util.function.Supplier;

/**
 * The registry acts as a simple map of components in the system. It's possible
 * to get an instance of a provider of an instance.
 */
public interface Registry
{
    /**
     * Returns a singleton instance of the supplied type. If not found, then an
     * exception is thrown.
     *
     * @param type type of instance.
     * @param <T>  type of instance.
     * @return the instance bound to the supplied type.
     * @throws IllegalArgumentException if the instance of type cannot be found.
     */
    <T> T getInstance( Class<T> type );

    /**
     * Returns a singleton instance of the supplied type. If not found, this method
     * will return null.
     *
     * @param type type of instance.
     * @param <T>  type of instance.
     * @return the instance bound to the supplied type or null.
     */
    <T> T getInstanceOrNull( Class<T> type );

    /**
     * Returns a provider of instances for the supplied type. This will always return
     * a provider even if the type is not bound. If the user tries to get an instance
     * of an unknown type, then an exception is thrown.
     *
     * @param type type of instance.
     * @param <T>  type of instance.
     * @return a provider bound to the supplied type.
     */
    <T> Supplier<T> getProvider( Class<T> type );
}
