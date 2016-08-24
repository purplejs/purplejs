package io.purplejs.http.internal.websocket;

import io.purplejs.http.websocket.WebSocketManager;
import io.purplejs.http.websocket.WebSocketSession;

public interface WebSocketRegistry
    extends WebSocketManager
{
    void add( WebSocketSession session );

    void remove( WebSocketSession session );
}
