package io.purplejs.http;

import java.util.List;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.Cookie;
import io.purplejs.http.Headers;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.core.value.ScriptValue;

final class ResponseImpl
    implements Response
{
    Status status;

    MediaType contentType;

    ByteSource body;

    Headers headers;

    ScriptValue value;

    List<Cookie> cookies;

    @Override
    public Status getStatus()
    {
        return this.status;
    }

    @Override
    public MediaType getContentType()
    {
        return this.contentType;
    }

    @Override
    public Headers getHeaders()
    {
        return this.headers;
    }

    @Override
    public List<Cookie> getCookies()
    {
        return this.cookies;
    }

    @Override
    public ScriptValue getValue()
    {
        return this.value;
    }

    @Override
    public ByteSource getBody()
    {
        return this.body;
    }
}
