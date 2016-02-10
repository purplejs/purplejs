package org.purplejs.http;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface Parameters
{
    Collection<String> get( String key );

    Optional<String> getFirst( String key );

    Map<String, Collection<String>> asMap();
}
