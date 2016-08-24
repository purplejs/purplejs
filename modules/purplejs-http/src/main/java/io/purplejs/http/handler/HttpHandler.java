package io.purplejs.http.handler;

import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.websocket.WebSocketEvent;

public interface HttpHandler
{
    Response serve( Request request );

    Response errorIfNeeded( Request request, Response response );

    Response handleException( Request request, Throwable cause );

    boolean handleEvent( WebSocketEvent event );
}
