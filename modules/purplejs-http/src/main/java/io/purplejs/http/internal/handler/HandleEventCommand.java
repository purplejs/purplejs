package io.purplejs.http.internal.handler;

import java.util.function.Function;

import io.purplejs.core.value.ScriptExports;
import io.purplejs.http.websocket.WebSocketEvent;

final class HandleEventCommand
    implements Function<ScriptExports, Boolean>
{
    private final static String EVENT_METHOD = "webSocketEvent";

    private final WebSocketEvent event;

    HandleEventCommand( final WebSocketEvent event )
    {
        this.event = event;
    }

    @Override
    public Boolean apply( final ScriptExports exports )
    {
        if ( !exports.hasMethod( EVENT_METHOD ) )
        {
            return false;
        }

        final JsonWebSocketEvent wrapper = new JsonWebSocketEvent( this.event );
        exports.executeMethod( EVENT_METHOD, wrapper );
        return true;
    }
}
