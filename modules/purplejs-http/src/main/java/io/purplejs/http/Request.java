package io.purplejs.http;

import com.google.common.io.ByteSource;

public interface Request
{
    String getMethod();

    Parameters getParameters();

    Headers getHeaders();

    ByteSource getBody();

    MultipartForm getMultipart();

    Object getRaw();
}
