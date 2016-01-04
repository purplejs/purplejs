package org.purplejs.http;

import java.util.Map;
import java.util.Optional;

public interface Headers
{
    Optional<String> get( String name );

    Map<String, String> asMap();
}
