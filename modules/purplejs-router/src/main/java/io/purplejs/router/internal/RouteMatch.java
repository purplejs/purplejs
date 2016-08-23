package io.purplejs.router.internal;

import java.util.Map;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public final class RouteMatch
{
    private final Map<String, String> pathParams;

    private final ScriptObjectMirror handler;

    public RouteMatch( final Map<String, String> pathParams, final ScriptObjectMirror handler )
    {
        this.pathParams = pathParams;
        this.handler = handler;
    }

    public ScriptObjectMirror getHandler()
    {
        return this.handler;
    }

    public void appendPathParams( final ScriptObjectMirror object )
    {
        for ( final Map.Entry<String, String> entry : this.pathParams.entrySet() )
        {
            object.put( entry.getKey(), entry.getValue() );
        }
    }
}
