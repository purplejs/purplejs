package io.purplejs.http;

import java.net.URI;
import java.util.Map;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.multipart.MultipartForm;

public interface Request
{
    String getMethod();

    URI getUri();

    Parameters getParameters();

    Headers getHeaders();

    Map<String, String> getCookies();

    MediaType getContentType();

    long getContentLength();

    ByteSource getBody();

    MultipartForm getMultipart();

    boolean isWebSocket();

    Object getRaw();
}
