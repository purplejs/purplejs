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

    SettingsBuilder put( String key, int value );

    SettingsBuilder put( String key, long value );

    SettingsBuilder put( String key, float value );

    SettingsBuilder put( String key, double value );

    SettingsBuilder putArray( String key, String... values );

    SettingsBuilder interpolate();

    Settings build();

    static SettingsBuilder newBuilder()
    {
        return new SettingsBuilderImpl();
    }
}
