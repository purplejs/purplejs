package io.purplejs.http.websocket;

import java.util.stream.Stream;

public interface WebSocketManager
{
    WebSocketSession getSession( String id );

    Stream<WebSocketSession> getByGroup( String group );
}
