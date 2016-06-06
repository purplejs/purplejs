package org.purplejs.impl.executor;

import java.util.Map;

import org.purplejs.resource.ResourceLoader;

public interface ScriptSettings
{
    boolean isDevMode();

    ResourceLoader getResourceLoader();

    ClassLoader getClassLoader();

    Map<String, Object> getGlobalVariables();
}
