package io.purplejs.websocket.internal;

import java.util.function.Function;

import io.purplejs.core.Engine;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;
import io.purplejs.http.Response;
import io.purplejs.http.internal.websocket.WebSocketRegistry;
import io.purplejs.http.websocket.WebSocketConfig;
import io.purplejs.http.websocket.WebSocketEvent;
import io.purplejs.http.websocket.WebSocketEventType;
import io.purplejs.http.websocket.WebSocketSession;
import io.purplejs.websocket.handler.WebSocketHandler;

final class WebSocketHandlerImpl
    implements WebSocketHandler
{
    Engine engine;

    ResourcePath resource;

    WebSocketRegistry registry;

    @Override
    public WebSocketConfig getConfig( final Response response )
    {
        return null;
    }

    private <R> R execute( final Function<ScriptExports, R> command )
    {
        final ScriptExports exports = this.engine.require( this.resource );
        return command.apply( exports );
    }

    @Override
    public boolean handleEvent( final WebSocketEvent event )
    {
        final WebSocketSession session = event.getSession();
        final WebSocketEventType type = event.getType();

        if ( type == WebSocketEventType.OPEN )
        {
            this.registry.add( session );
        }
        else if ( type == WebSocketEventType.CLOSE )
        {
            this.registry.remove( session );
        }

        final HandleEventCommand command = new HandleEventCommand( event );
        return execute( command );
    }
}
