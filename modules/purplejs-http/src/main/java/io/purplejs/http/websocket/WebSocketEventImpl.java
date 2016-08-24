package io.purplejs.http.websocket;

import com.google.common.io.ByteSource;

final class WebSocketEventImpl
    implements WebSocketEvent
{
    WebSocketEventType type;

    WebSocketSession session;

    String message;

    ByteSource binary;

    Integer closeCode;

    String closeReason;

    Throwable error;

    @Override
    public WebSocketEventType getType()
    {
        return this.type;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }

    @Override
    public ByteSource getBinary()
    {
        return this.binary;
    }

    @Override
    public Integer getCloseCode()
    {
        return this.closeCode;
    }

    @Override
    public String getCloseReason()
    {
        return this.closeReason;
    }

    @Override
    public Throwable getError()
    {
        return this.error;
    }

    @Override
    public WebSocketSession getSession()
    {
        return this.session;
    }
}
