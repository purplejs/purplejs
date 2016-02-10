package org.purplejs.http;

import java.util.Map;
import java.util.Optional;

import com.google.common.net.MediaType;

public interface Headers
{
    Optional<String> get( String key );

    Optional<MediaType> getContentType();

    Map<String, String> asMap();
}
