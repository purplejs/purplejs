package org.purplejs.http;

import java.util.Map;
import java.util.Optional;

import com.google.common.net.MediaType;

public interface Headers
{
    Optional<String> get( String key );

    default Optional<MediaType> getContentType()
    {
        return get( "Content-Type" ).map( MediaType::parse );
    }

    Map<String, String> asMap();
}
