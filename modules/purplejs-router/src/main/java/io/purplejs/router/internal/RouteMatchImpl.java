package io.purplejs.router.internal;

import java.util.Map;

import jdk.nashorn.api.scripting.JSObject;

final class RouteMatchImpl
    implements RouteMatch
{
    private final Map<String, String> pathParams;

    private final JSObject handler;

    RouteMatchImpl( final Map<String, String> pathParams, final JSObject handler )
    {
        this.pathParams = pathParams;
        this.handler = handler;
    }

    @Override
    public JSObject getHandler()
    {
        return this.handler;
    }

    @Override
    public void appendPathParams( final JSObject object )
    {
        for ( final Map.Entry<String, String> entry : this.pathParams.entrySet() )
        {
            object.setMember( entry.getKey(), entry.getValue() );
        }
    }
}
