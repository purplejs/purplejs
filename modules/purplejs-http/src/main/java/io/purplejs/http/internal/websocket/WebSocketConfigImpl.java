package io.purplejs.http.internal.websocket;

import java.util.Set;

import io.purplejs.core.value.ScriptValue;
import io.purplejs.http.websocket.WebSocketConfig;

final class WebSocketConfigImpl
    implements WebSocketConfig
{
    String group;

    long timeout;

    Set<String> subProtocols;

    ScriptValue attributes;

    @Override
    public String getGroup()
    {
        return this.group;
    }

    @Override
    public long getTimeout()
    {
        return this.timeout;
    }

    @Override
    public Set<String> getSubProtocols()
    {
        return this.subProtocols;
    }

    @Override
    public ScriptValue getAttributes()
    {
        return this.attributes;
    }
}
