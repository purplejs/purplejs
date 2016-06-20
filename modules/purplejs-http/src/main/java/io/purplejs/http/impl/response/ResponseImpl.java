package io.purplejs.http.impl.response;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.Headers;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.value.ScriptValue;

final class ResponseImpl
    implements Response
{
    Status status;

    MediaType contentType;

    ByteSource body;

    Headers headers;

    ScriptValue value;

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
