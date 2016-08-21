package io.purplejs.http.internal.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.core.value.ScriptValue;
import io.purplejs.http.Cookie;
import io.purplejs.http.Headers;
import io.purplejs.http.Response;
import io.purplejs.http.ResponseBuilder;
import io.purplejs.http.Status;

public final class ResponseBuilderImpl
    implements ResponseBuilder
{
    private Status status;

    private MediaType contentType;

    private ByteSource body;

    private final Headers headers = new Headers();

    private final List<Cookie> cookies = Lists.newArrayList();

    private ScriptValue value;

    @Override
    public ResponseBuilder status( final Status status )
    {
        this.status = status;
        return this;
    }

    @Override
    public ResponseBuilder contentType( final MediaType contentType )
    {
        this.contentType = contentType;
        return this;
    }

    @Override
    public ResponseBuilder body( final ByteSource body )
    {
        this.body = body;
        return this;
    }

    @Override
    public ResponseBuilder header( final String name, final String value )
    {
        this.headers.put( name, value );
        return this;
    }

    @Override
    public ResponseBuilder value( final ScriptValue value )
    {
        this.value = value;
        return this;
    }

    @Override
    public ResponseBuilder cookie( final Cookie cookie )
    {
        this.cookies.add( cookie );
        return this;
    }

    @Override
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

    public static ResponseBuilderImpl newBuilder()
    {
        return new ResponseBuilderImpl();
    }
}
