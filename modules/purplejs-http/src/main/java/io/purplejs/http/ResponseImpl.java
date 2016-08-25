package io.purplejs.http;

import java.util.Map;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.core.value.ScriptValue;
import io.purplejs.http.websocket.WebSocketConfig;

final class ResponseImpl
    implements Response
{
    Status status;

    MediaType contentType;

    ByteSource body;

    Headers headers;

    ScriptValue value;

    Map<String, Cookie> cookies;

    WebSocketConfig webSocket;

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
    public Map<String, Cookie> getCookies()
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

    @Override
    public WebSocketConfig getWebSocket()
    {
        return this.webSocket;
    }
}
