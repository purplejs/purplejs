package io.purplejs.http;

import java.net.URI;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

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

    Object getRaw();
}
