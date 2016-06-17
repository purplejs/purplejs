package io.purplejs.http;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

public interface MultipartItem
{
    String getName();

    String getFileName();

    MediaType getContentType();

    ByteSource getBytes();

    String getAsString();

    long getSize();
}
