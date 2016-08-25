package io.purplejs.http.websocket;

public enum WebSocketEventType
{
    // Rename MESSAGE to TEXT (and also the accessors for event)
    OPEN, CLOSE, ERROR, MESSAGE, BINARY
}
