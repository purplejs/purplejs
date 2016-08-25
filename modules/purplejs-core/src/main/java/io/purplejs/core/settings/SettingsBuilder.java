package io.purplejs.core.settings;

import java.util.Map;
import java.util.Properties;

import io.purplejs.core.internal.settings.SettingsBuilderImpl;

public interface SettingsBuilder
{
    SettingsBuilder put( Properties properties );

    SettingsBuilder put( Map<String, String> map );

    SettingsBuilder put( Settings settings );

    SettingsBuilder put( String key, Properties properties );

    SettingsBuilder put( String key, Map<String, String> map );

    SettingsBuilder put( String key, Settings settings );

    SettingsBuilder put( String key, String value );

    SettingsBuilder put( String key, boolean value );

    SettingsBuilder put( String key, Number value );

    SettingsBuilder putArray( String key, String... values );

    Settings build();

    static SettingsBuilder newBuilder()
    {
        return new SettingsBuilderImpl();
    }
}
