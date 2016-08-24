package io.purplejs.http;

import java.util.List;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.core.value.ScriptValue;
import io.purplejs.http.websocket.WebSocketConfig;

public interface Response
{
    Status getStatus();

    ByteSource getBody();

    MediaType getContentType();

    Headers getHeaders();

    List<Cookie> getCookies();

    WebSocketConfig getWebSocket();

    ScriptValue getValue();
}
