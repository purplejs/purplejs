package io.purplejs.http.internal.lib;

import com.google.common.io.ByteSource;

import io.purplejs.http.websocket.WebSocketManager;
import io.purplejs.http.websocket.WebSocketSession;

public final class WebSocketLibHelper
{
    private WebSocketManager manager;

    public void setManager( final WebSocketManager manager )
    {
        this.manager = manager;
    }

    public void sendMessage( final String id, final Object message )
    {
        sendMessage( this.manager.getSession( id ), message );
    }

    public void sendMessageToGroup( final String group, final Object message, final String excludeId )
    {
        this.manager.getByGroup( group ).
            filter( session -> ( excludeId == null ) || !session.getId().equals( excludeId ) ).
            forEach( session -> sendMessage( session, message ) );
    }

    private void sendMessage( final WebSocketSession session, final Object message )
    {
        if ( session == null )
        {
            return;
        }

        if ( message == null )
        {
            return;
        }

        if ( message instanceof ByteSource )
        {
            session.send( (ByteSource) message );
        }
        else
        {
            session.send( message.toString() );
        }
    }
}
