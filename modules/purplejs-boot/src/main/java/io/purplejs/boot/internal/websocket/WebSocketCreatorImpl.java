package io.purplejs.boot.internal.websocket;

import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import com.google.common.io.ByteSource;

import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.websocket.WebSocketConfig;
import io.purplejs.http.websocket.WebSocketEvent;
import io.purplejs.http.websocket.WebSocketEventBuilder;

public final class WebSocketCreatorImpl
    implements WebSocketCreator, WebSocketListener
{
    private final WebSocketConfig config;

    private final HttpHandler handler;

    private WebSocketSessionImpl session;

    public WebSocketCreatorImpl( final HttpHandler handler, final WebSocketConfig config )
    {
        this.config = config;
        this.handler = handler;
    }

    public Object createWebSocket( final ServletUpgradeRequest req, final ServletUpgradeResponse res )
    {
        if ( this.config == null )
        {
            return null;
        }

        final Set<String> allowSubProtocols = this.config.getSubProtocols();
        if ( allowSubProtocols.isEmpty() )
        {
            return doCreateWebSocket();
        }

        for ( final String protocol : req.getSubProtocols() )
        {
            if ( allowSubProtocols.contains( protocol ) )
            {
                res.setAcceptedSubProtocol( protocol );
                return doCreateWebSocket();
            }
        }

        return null;
    }

    private WebSocketListener doCreateWebSocket()
    {
        return this;
    }

    @Override
    public void onWebSocketBinary( final byte[] payload, final int offset, final int len )
    {
        final byte[] bytes = new byte[len];
        System.arraycopy( payload, offset, bytes, 0, len );

        final WebSocketEvent event = newEventBuilder().
            binaryEvent( ByteSource.wrap( bytes ) ).
            build();

        handleEvent( event );
    }

    @Override
    public void onWebSocketText( final String message )
    {
        final WebSocketEvent event = newEventBuilder().
            messageEvent( message ).
            build();

        handleEvent( event );
    }

    @Override
    public void onWebSocketClose( final int statusCode, final String reason )
    {
        final WebSocketEvent event = newEventBuilder().
            closeEvent( statusCode, reason ).
            build();

        handleEvent( event );
    }

    @Override
    public void onWebSocketConnect( final Session session )
    {
        session.setIdleTimeout( this.config.getTimeout() );
        this.session = newSession( session );

        final WebSocketEvent event = newEventBuilder().
            openEvent().
            build();

        handleEvent( event );
    }

    @Override
    public void onWebSocketError( final Throwable cause )
    {
        final WebSocketEvent event = newEventBuilder().
            errorEvent( cause ).
            build();

        handleEvent( event );
    }

    private WebSocketEventBuilder newEventBuilder()
    {
        final WebSocketEventBuilder builder = WebSocketEventBuilder.newBuilder();
        builder.session( this.session );
        return builder;
    }

    private WebSocketSessionImpl newSession( final Session session )
    {
        final WebSocketSessionImpl result = new WebSocketSessionImpl();
        result.group = this.config.getGroup();
        result.attributes = this.config.getAttributes();
        result.raw = session;
        return result;
    }

    private void handleEvent( final WebSocketEvent event )
    {
        this.handler.handleEvent( event );
    }
}
