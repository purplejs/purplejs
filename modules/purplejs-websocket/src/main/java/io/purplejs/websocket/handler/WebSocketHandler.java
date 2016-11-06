package io.purplejs.websocket.handler;

import io.purplejs.http.Response;
import io.purplejs.http.websocket.WebSocketConfig;
import io.purplejs.http.websocket.WebSocketEvent;

public interface WebSocketHandler
{
    WebSocketConfig getConfig( Response response );

    boolean handleEvent( WebSocketEvent event );
}
