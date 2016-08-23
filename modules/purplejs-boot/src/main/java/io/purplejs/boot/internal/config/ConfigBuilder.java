package io.purplejs.boot.internal.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public final class ConfigBuilder
{
    public Config build()
    {
        return ConfigFactory.load();
    }
}
