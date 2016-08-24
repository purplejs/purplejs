package io.purplejs.http.websocket;

import com.google.common.io.ByteSource;

import io.purplejs.core.value.ScriptValue;

public interface WebSocketSession
{
    String getId();

    String getGroup();

    ScriptValue getAttributes();

    boolean isOpen();

    String getSubProtocol();

    void send( String message );

    void send( ByteSource bytes );

    void close();

    void close( int code, String reason );

    Object getRaw();
}
