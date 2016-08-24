package io.purplejs.http.websocket;

import com.google.common.io.ByteSource;

public final class WebSocketEventBuilder
{
    private WebSocketEventType type;

    private Integer closeCode;

    private String closeReason;

    private String message;

    private ByteSource binary;

    private Throwable error;

    private WebSocketSession session;

    public WebSocketEventBuilder openEvent()
    {
        this.type = WebSocketEventType.OPEN;
        return this;
    }

    public WebSocketEventBuilder closeEvent( final int code, final String reason )
    {
        this.type = WebSocketEventType.CLOSE;
        this.closeCode = code;
        this.closeReason = reason;
        return this;
    }

    public WebSocketEventBuilder errorEvent( final Throwable cause )
    {
        this.type = WebSocketEventType.ERROR;
        this.error = cause;
        return this;
    }

    public WebSocketEventBuilder messageEvent( final String message )
    {
        this.type = WebSocketEventType.MESSAGE;
        this.message = message;
        return this;
    }

    public WebSocketEventBuilder binaryEvent( final ByteSource binary )
    {
        this.type = WebSocketEventType.BINARY;
        this.binary = binary;
        return this;
    }

    public WebSocketEventBuilder session( final WebSocketSession session )
    {
        this.session = session;
        return this;
    }

    public WebSocketEvent build()
    {
        final WebSocketEventImpl event = new WebSocketEventImpl();
        event.type = this.type;
        event.closeCode = this.closeCode;
        event.closeReason = this.closeReason;
        event.message = this.message;
        event.binary = this.binary;
        event.error = this.error;
        event.session = this.session;
        return event;
    }

    public static WebSocketEventBuilder newBuilder()
    {
        return new WebSocketEventBuilder();
    }
}
