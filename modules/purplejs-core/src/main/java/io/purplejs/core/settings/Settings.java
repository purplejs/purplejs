package io.purplejs.core.settings;

import java.util.Map;
import java.util.Optional;

public interface Settings
{
    Optional<String> get( String key );

    <T> Optional<T> get( Class<T> type, String key );

    Settings getAsSettings( String key );

    Map<String, String> asMap();
}
