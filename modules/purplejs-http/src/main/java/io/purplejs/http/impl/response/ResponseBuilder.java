package io.purplejs.http.impl.response;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.Response;
import io.purplejs.http.Status;

public final class ResponseBuilder
{
    private Status status;

    private MediaType contentType;

    private ByteSource body;

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

    public ResponseBuilder body( final Object body )
    {
        this.body = ByteSource.wrap( body.toString().getBytes( Charsets.UTF_8 ) );
        return this;
    }

    public Response build()
    {
        final ResponseImpl response = new ResponseImpl();
        response.status = this.status;
        response.contentType = this.contentType;
        response.body = this.body;
        return response;
    }

    public static ResponseBuilder newBuilder()
    {
        return new ResponseBuilder();
    }
}
