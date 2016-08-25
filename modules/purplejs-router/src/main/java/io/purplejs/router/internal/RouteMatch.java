package io.purplejs.router.internal;

import jdk.nashorn.api.scripting.JSObject;

@SuppressWarnings("WeakerAccess")
public interface RouteMatch
{
    JSObject getHandler();

    void appendPathParams( JSObject object );
}
