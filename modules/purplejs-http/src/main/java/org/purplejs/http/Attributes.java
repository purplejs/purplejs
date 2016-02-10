package org.purplejs.http;

import java.util.Map;
import java.util.Optional;

public interface Attributes
{
    Optional<Object> get( String key );

    void set( String key, Object value );

    void remove( String key );

    <T> Optional<T> get( Class<T> key );

    <T> void set( Class<T> key, T value );

    <T> void remove( Class<T> key );

    Map<String, Object> asMap();
}
