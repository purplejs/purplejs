package io.purplejs.websocket.internal;

import io.purplejs.core.json.JsonGenerator;
import io.purplejs.core.json.JsonSerializable;
import io.purplejs.http.websocket.WebSocketEvent;
import io.purplejs.http.websocket.WebSocketSession;

final class JsonWebSocketEvent
    implements JsonSerializable
{
    private final WebSocketEvent event;

    JsonWebSocketEvent( final WebSocketEvent event )
    {
        this.event = event;
    }

    @Override
    public void serialize( final JsonGenerator gen )
    {
        gen.map();
        gen.value( "type", this.event.getType().name().toLowerCase() );
        gen.value( "message", this.event.getMessage() );
        gen.value( "binary", this.event.getBinary() );
        gen.value( "closeCode", this.event.getCloseCode() );
        gen.value( "closeReason", this.event.getCloseReason() );
        serializeError( gen, this.event.getError() );

        serializeSession( gen );
        gen.end();
    }

    private void serializeError( final JsonGenerator gen, final Throwable error )
    {
        gen.value( "error", error != null ? error.getMessage() : null );
    }

    private void serializeSession( final JsonGenerator gen )
    {
        final WebSocketSession session = this.event.getSession();

        gen.map( "session" );
        gen.value( "id", session.getId() );
        gen.value( "group", session.getGroup() );
        gen.value( "subProtocol", session.getSubProtocol() );
        gen.value( "attributes", session.getAttributes() );
        gen.value( "wrapped", session );
        gen.end();
    }
}
