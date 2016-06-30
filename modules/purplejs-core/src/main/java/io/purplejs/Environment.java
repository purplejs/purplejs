package io.purplejs;

import java.util.Map;

import io.purplejs.registry.Registry;
import io.purplejs.resource.ResourceLoader;

public interface Environment
    extends Registry
{
    boolean isDevMode();

    ResourceLoader getResourceLoader();

    ClassLoader getClassLoader();

    Map<String, String> getConfig();
}
