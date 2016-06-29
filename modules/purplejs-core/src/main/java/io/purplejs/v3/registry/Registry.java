package io.purplejs.v3.registry;

import java.util.function.Supplier;

/**
 * In JavaScript:
 *
 *   __.registry
 *   __.environment
 *   __.engine
 *   __.resource
 *   __.resourceLoader
 *
 *   __.getInstance('com.foo.bar');
 *   __.getInstance(Java.type('com.foo.bar'));
 *
 *   __.newInstance('com.foo.bar');
 *   __.newInstance(Java.type('com.foo.bar'));
 *
 *   __.getSupplier('com.foo.bar');
 *   __.getSupplier(Java.type('com.foo.bar'));
 *
 *   __.disposer(..)
 *
 */


public interface Registry
{
    <T> T getInstance( Class<T> type );

    <T> Supplier<T> getSupplier( Class<T> type );

    <T> T newInstance( Class<T> type );
}
