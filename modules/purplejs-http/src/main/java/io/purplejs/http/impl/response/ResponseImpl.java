package io.purplejs.http.impl.response;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.Attributes;
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
        return null;
    }

    @Override
    public Attributes getAttributes()
    {
        return null;
    }

    @Override
    public ScriptValue getValue()
    {
        return null;
    }

    @Override
    public ByteSource getBody()
    {
        return this.body;
    }
}
