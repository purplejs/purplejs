package io.purplejs.boot.internal.config;

// TODO: Load using properties and yaml

// 1) Apply System.properties
// 2) Load from default.properties
// 3) Load from classpath
// 4) Load from home-dir
// 5) Interpolate


// 1) application.properties
// 2) application-<mode>.properties


public final class ConfigurationLoader
{
    public Configuration load()
    {
        return new Configuration();
    }
}
