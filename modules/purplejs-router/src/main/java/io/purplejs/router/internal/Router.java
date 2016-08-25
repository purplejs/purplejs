package io.purplejs.router.internal;

import java.util.List;

import com.google.common.collect.Lists;

import jdk.nashorn.api.scripting.JSObject;

@SuppressWarnings("WeakerAccess")
public final class Router
{
    private final List<Route> list;

    public Router()
    {
        this.list = Lists.newArrayList();
    }

    public void add( final String method, final String pattern, final JSObject handler )
    {
        final RoutePattern routePattern = RoutePattern.compile( pattern );
        this.list.add( new Route( method, routePattern, handler ) );
    }

    public RouteMatch matches( final String method, final String path )
    {
        for ( final Route route : this.list )
        {
            if ( route.matches( method, path ) )
            {
                return newRouteMatch( route, path );
            }
        }

        if ( method.equals( "HEAD" ) )
        {
            return matches( "GET", path );
        }

        return null;
    }

    private RouteMatch newRouteMatch( final Route route, final String path )
    {
        return new RouteMatchImpl( route.getPathParams( path ), route.getHandler() );
    }
}
