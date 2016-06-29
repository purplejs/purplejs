package io.purplejs.v3;

import java.util.Map;
import java.util.function.Supplier;

import io.purplejs.resource.ResourceLoader;

public interface Environment
{
    boolean isDevMode();

    ResourceLoader getResourceLoader();

    ClassLoader getClassLoader();

    Map<String, String> getConfig();

    <T> T getInstance( Class<T> type );

    <T> Supplier<T> getSupplier( Class<T> type );

    <T> T newInstance( Class<T> type );
}
