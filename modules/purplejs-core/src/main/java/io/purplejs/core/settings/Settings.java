package io.purplejs.core.settings;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface Settings
{
    Optional<String> get( String key );

    <T> Optional<T> get( Class<T> type, String key );

    <T> Optional<T> get( Class<T> type, String key, Function<String, T> converter );

    List<String> getAsArray( String key );

    <T> List<T> getAsArray( Class<T> type, String key );

    <T> List<T> getAsArray( Class<T> type, String key, Function<String, T> converter );

    Settings getAsSettings( String key );

    Map<String, String> asMap();
}
