package io.purplejs.core;

import java.util.Map;

import io.purplejs.core.registry.Registry;
import io.purplejs.core.resource.ResourceLoader;

public interface Environment
    extends Registry
{
    ResourceLoader getResourceLoader();

    ClassLoader getClassLoader();

    Map<String, String> getConfig();
}
