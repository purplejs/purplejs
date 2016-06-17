package io.purplejs;

import java.util.Map;

import io.purplejs.resource.ResourceLoader;

public interface ScriptSettings
{
    boolean isDevMode();

    ResourceLoader getResourceLoader();

    ClassLoader getClassLoader();

    Map<String, Object> getGlobalVariables();

    Map<String, String> getConfig();
}
