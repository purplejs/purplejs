package io.purplejs.http;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.core.value.ScriptValue;
import io.purplejs.http.websocket.WebSocketConfig;

public final class ResponseBuilder
{
    private Status status;

    private MediaType contentType;

    private ByteSource body;

    private final Headers headers = new Headers();

    private final Map<String, Cookie> cookies = Maps.newHashMap();

    private WebSocketConfig webSocket;

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
        this.headers.put( name, value );
        return this;
    }

    public ResponseBuilder value( final ScriptValue value )
    {
        this.value = value;
        return this;
    }

    public ResponseBuilder cookie( final Cookie cookie )
    {
        this.cookies.put( cookie.getName(), cookie );
        return this;
    }

    public ResponseBuilder webSocket( final WebSocketConfig webSocket )
    {
        this.webSocket = webSocket;
        return this;
    }

    public Response build()
    {
        final ResponseImpl response = new ResponseImpl();
        response.status = this.status != null ? this.status : Status.OK;
        response.contentType = this.contentType != null ? this.contentType : MediaType.OCTET_STREAM;
        response.body = this.body;
        response.headers = this.headers;
        response.value = this.value;
        response.cookies = this.cookies;
        response.webSocket = this.webSocket;
        return response;
    }

    public static ResponseBuilder newBuilder()
    {
        return new ResponseBuilder();
    }
}
