package io.purplejs.http;

import java.net.URI;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.multipart.MultipartForm;

public final class RequestBuilder
{
    private String method;

    private URI uri;

    private final Parameters parameters;

    private final Headers headers;

    private final Map<String, String> cookies;

    private MediaType contentType;

    private long contentLength;

    private ByteSource body;

    private boolean webSocket;

    private MultipartForm multipart;

    private Object raw;

    private RequestBuilder()
    {
        this.parameters = new Parameters();
        this.headers = new Headers();
        this.cookies = Maps.newHashMap();
        this.contentLength = 0;
        this.webSocket = false;
    }

    public RequestBuilder method( final String method )
    {
        this.method = method.toUpperCase();
        return this;
    }

    public RequestBuilder uri( final String uri )
    {
        return uri( URI.create( uri ) );
    }

    public RequestBuilder uri( final URI uri )
    {
        this.uri = uri;
        return this;
    }

    public RequestBuilder parameter( final String name, final String value )
    {
        this.parameters.put( name, value );
        return this;
    }

    public RequestBuilder header( final String name, final String value )
    {
        this.headers.put( name, value );
        return this;
    }

    public RequestBuilder cookie( final String name, final String value )
    {
        this.cookies.put( name, value );
        return this;
    }

    public RequestBuilder contentType( final String contentType )
    {
        return contentType( contentType != null ? MediaType.parse( contentType ) : null );
    }

    public RequestBuilder contentType( final MediaType contentType )
    {
        this.contentType = contentType;
        return this;
    }

    public RequestBuilder contentLength( final long contentLength )
    {
        this.contentLength = contentLength;
        return this;
    }

    public RequestBuilder body( final String body )
    {
        this.body = body != null ? ByteSource.wrap( body.getBytes( Charsets.UTF_8 ) ) : null;
        return this;
    }

    public RequestBuilder body( final ByteSource body )
    {
        this.body = body;
        return this;
    }

    public RequestBuilder webSocket( final boolean webSocket )
    {
        this.webSocket = webSocket;
        return this;
    }

    public RequestBuilder multipart( final MultipartForm multipart )
    {
        this.multipart = multipart;
        return this;
    }

    public RequestBuilder raw( final Object raw )
    {
        this.raw = raw;
        return this;
    }

    public Request build()
    {
        final RequestImpl request = new RequestImpl();
        request.method = this.method != null ? this.method : "GET";
        request.uri = this.uri != null ? this.uri : URI.create( "http://localhost:8080" );
        request.parameters = this.parameters;
        request.headers = this.headers;
        request.cookies = this.cookies;
        request.contentType = this.contentType;
        request.contentLength = this.contentLength;
        request.body = this.body != null ? this.body : ByteSource.empty();
        request.webSocket = this.webSocket;
        request.raw = this.raw;
        request.multipart = this.multipart;
        return request;
    }

    public static RequestBuilder newBuilder()
    {
        return new RequestBuilder();
    }
}
