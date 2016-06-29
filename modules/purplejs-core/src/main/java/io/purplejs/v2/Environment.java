package io.purplejs.v2;

import java.util.Map;

import javax.inject.Provider;

import io.purplejs.resource.ResourceLoader;

public interface Environment
{
    RunMode getRunMode();

    ResourceLoader getResourceLoader();

    ClassLoader getClassLoader();

    Map<String, Object> getGlobalVariables();

    Map<String, String> getConfig();

    // TODO: Rename to getInstance
    <T> T resolve( Class<T> type );

    // TODO: Rename to getProvider
    <T> Provider<T> provider( Class<T> type );

    // <T> T newInstance( Class<T> type );
}
