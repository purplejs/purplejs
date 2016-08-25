package io.purplejs.router.internal;

import java.util.Map;

import jdk.nashorn.api.scripting.JSObject;

final class Route
{
    private final String method;

    private final RoutePattern pattern;

    private final JSObject handler;

    Route( final String method, final RoutePattern pattern, final JSObject handler )
    {
        this.method = method.equals( "*" ) ? null : method;
        this.pattern = pattern;
        this.handler = handler;
    }

    boolean matches( final String method, final String path )
    {
        final boolean matchesMethod = ( this.method == null ) || this.method.equals( method );
        return matchesMethod && this.pattern.matches( path );
    }

    Map<String, String> getPathParams( final String path )
    {
        return this.pattern.getPathParams( path );
    }

    JSObject getHandler()
    {
        return this.handler;
    }
}
