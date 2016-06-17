package io.purplejs.impl;

import java.util.Map;

import org.purplejs.engine.ScriptSettings;
import org.purplejs.resource.ResourceLoader;

import com.google.common.collect.ImmutableMap;

final class ScriptSettingsImpl
    implements ScriptSettings
{
    boolean devMode;

    ResourceLoader resourceLoader;

    ClassLoader classLoader;

    ImmutableMap<String, String> config;

    ImmutableMap<String, Object> globalVariables;

    @Override
    public boolean isDevMode()
    {
        return this.devMode;
    }

    @Override
    public ResourceLoader getResourceLoader()
    {
        return this.resourceLoader;
    }

    @Override
    public ClassLoader getClassLoader()
    {
        return this.classLoader;
    }

    @Override
    public Map<String, Object> getGlobalVariables()
    {
        return this.globalVariables;
    }

    @Override
    public Map<String, String> getConfig()
    {
        return this.config;
    }
}
