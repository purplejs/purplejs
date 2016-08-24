package io.purplejs.http;

import java.net.URI;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

// TODO: Use optional on getContentType, getBody and getMultipart.
public interface Request
{
    String getMethod();

    URI getUri();

    Parameters getParameters();

    Headers getHeaders();

    MediaType getContentType();

    long getContentLength();

    ByteSource getBody();

    MultipartForm getMultipart();

    boolean isWebSocket();

    Object getRaw();
}
