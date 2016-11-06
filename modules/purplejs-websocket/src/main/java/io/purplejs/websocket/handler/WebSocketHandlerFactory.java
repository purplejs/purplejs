package io.purplejs.websocket.handler;

import io.purplejs.core.resource.ResourcePath;

public interface WebSocketHandlerFactory
{
    WebSocketHandler newHandler( ResourcePath resource );
}
