package io.purplejs.core.settings;

import java.util.Map;

public interface Settings
{
    String get( String key );

    String get( String key, String defValue );

    <T> T get( Class<T> type, String key );

    <T> T get( Class<T> type, String key, T defValue );

    Settings getAsSettings( String key );

    Map<String, String> asMap();
}
