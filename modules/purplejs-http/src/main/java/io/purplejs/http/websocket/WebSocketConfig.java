package io.purplejs.http.websocket;

import java.util.Set;

import io.purplejs.core.value.ScriptValue;

public interface WebSocketConfig
{
    String getGroup();

    long getTimeout();

    Set<String> getSubProtocols();

    ScriptValue getAttributes();
}
