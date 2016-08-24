package io.purplejs.http.websocket;

import com.google.common.io.ByteSource;

public interface WebSocketEvent
{
    WebSocketEventType getType();

    String getMessage();

    ByteSource getBinary();

    Integer getCloseCode();

    String getCloseReason();

    Throwable getError();

    WebSocketSession getSession();
}
