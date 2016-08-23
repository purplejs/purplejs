package io.purplejs.router.internal;

import java.util.Map;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public final class Route
{
    final String method;

    private final RoutePattern pattern;

    private final ScriptObjectMirror handler;

    public Route( final String method, final RoutePattern pattern, final ScriptObjectMirror handler )
    {
        this.method = method.equals( "*" ) ? null : method;
        this.pattern = pattern;
        this.handler = handler;
    }

    public boolean matches( final String method, final String path )
    {
        final boolean matchesMethod = ( this.method == null ) || this.method.equals( method );
        return matchesMethod && this.pattern.matches( path );
    }

    public Map<String, String> getPathParams( final String path )
    {
        return this.pattern.getPathParams( path );
    }

    public ScriptObjectMirror getHandler()
    {
        return this.handler;
    }
}
