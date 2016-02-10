package org.purplejs.http;

import com.google.common.io.ByteSource;

public interface Request
{
    String getMethod();

    Parameters getParameters();

    Headers getHeaders();

    Attributes getAttributes();

    ByteSource getBody();

    MultipartForm getMultipart();

    Object getRaw();
}
