package io.purplejs.http;

import java.net.URI;
import java.util.Map;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.multipart.MultipartForm;

final class RequestImpl
    implements Request
{
    String method;

    URI uri;

    Parameters parameters;

    Headers headers;

    Map<String, String> cookies;

    MediaType contentType;

    long contentLength;

    ByteSource body;

    MultipartForm multipart;

    boolean webSocket;

    Object raw;

    @Override
    public String getMethod()
    {
        return this.method;
    }

    @Override
    public URI getUri()
    {
        return this.uri;
    }

    @Override
    public Parameters getParameters()
    {
        return this.parameters;
    }

    @Override
    public Headers getHeaders()
    {
        return this.headers;
    }

    @Override
    public Map<String, String> getCookies()
    {
        return this.cookies;
    }

    @Override
    public MediaType getContentType()
    {
        return this.contentType;
    }

    @Override
    public long getContentLength()
    {
        return this.contentLength;
    }

    @Override
    public ByteSource getBody()
    {
        return this.body;
    }

    @Override
    public MultipartForm getMultipart()
    {
        return this.multipart;
    }

    @Override
    public boolean isWebSocket()
    {
        return this.webSocket;
    }

    @Override
    public Object getRaw()
    {
        return this.raw;
    }
}
