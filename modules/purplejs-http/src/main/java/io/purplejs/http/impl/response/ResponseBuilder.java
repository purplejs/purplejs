package io.purplejs.http.impl.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.Cookie;
import io.purplejs.http.Headers;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.value.ScriptValue;

public final class ResponseBuilder
{
    private Status status;

    private MediaType contentType;

    private ByteSource body;

    private final Headers headers = new Headers();

    private final List<Cookie> cookies = Lists.newArrayList();

    private ScriptValue value;

    public ResponseBuilder status( final Status status )
    {
        this.status = status;
        return this;
    }

    public ResponseBuilder contentType( final MediaType contentType )
    {
        this.contentType = contentType;
        return this;
    }

    public ResponseBuilder body( final ByteSource body )
    {
        this.body = body;
        return this;
    }

    public ResponseBuilder header( final String name, final String value )
    {
        this.headers.set( name, value );
        return this;
    }

    public ResponseBuilder value( final ScriptValue value )
    {
        this.value = value;
        return this;
    }

    public ResponseBuilder cookie( final Cookie cookie )
    {
        this.cookies.add( cookie );
        return this;
    }

    public Response build()
    {
        final ResponseImpl response = new ResponseImpl();
        response.status = this.status != null ? this.status : Status.OK;
        response.contentType = this.contentType != null ? this.contentType : MediaType.OCTET_STREAM;
        response.body = this.body != null ? this.body : ByteSource.empty();
        response.headers = this.headers;
        response.value = this.value;
        response.cookies = this.cookies;
        return response;
    }

    public static ResponseBuilder newBuilder()
    {
        return new ResponseBuilder();
    }
}
