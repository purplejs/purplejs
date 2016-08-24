package io.purplejs.boot.internal.websocket;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteSource;

import io.purplejs.core.value.ScriptValue;
import io.purplejs.http.websocket.WebSocketSession;

final class WebSocketSessionImpl
    implements WebSocketSession
{
    private final static Logger LOG = LoggerFactory.getLogger( WebSocketSessionImpl.class );

    private final String id;

    String group;

    ScriptValue attributes;

    Session raw;

    WebSocketSessionImpl()
    {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getId()
    {
        return this.id;
    }

    @Override
    public String getGroup()
    {
        return this.group;
    }

    @Override
    public ScriptValue getAttributes()
    {
        return this.attributes;
    }

    @Override
    public void close()
    {
        this.raw.close();
    }

    @Override
    public void close( final int code, final String reason )
    {
        this.raw.close( code, reason );
    }

    @Override
    public boolean isOpen()
    {
        return this.raw.isOpen();
    }

    @Override
    public String getSubProtocol()
    {
        return this.raw.getUpgradeResponse().getAcceptedSubProtocol();
    }

    private RemoteEndpoint getRemote()
    {
        try
        {
            return this.raw.getRemote();
        }
        catch ( final Exception e )
        {
            return null;
        }
    }

    @Override
    public void send( final String message )
    {
        final RemoteEndpoint endpoint = getRemote();
        if ( endpoint == null )
        {
            return;
        }

        try
        {
            endpoint.sendString( message );
        }
        catch ( final Exception e )
        {
            LOG.error( "Failed to send web-socket message", e );
        }
    }

    @Override
    public void send( final ByteSource bytes )
    {
        final RemoteEndpoint endpoint = getRemote();
        if ( endpoint == null )
        {
            return;
        }

        try
        {
            final ByteBuffer buffer = ByteBuffer.wrap( bytes.read() );
            endpoint.sendBytes( buffer );
        }
        catch ( final Exception e )
        {
            LOG.error( "Failed to send web-socket message", e );
        }
    }

    @Override
    public Object getRaw()
    {
        return this.raw;
    }
}
