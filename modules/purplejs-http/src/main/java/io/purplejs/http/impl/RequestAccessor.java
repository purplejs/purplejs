package io.purplejs.http.impl;

import java.util.function.Supplier;

import io.purplejs.http.Request;

public final class RequestAccessor
    implements Supplier<Request>
{
    private final static ThreadLocal<Request> CURRENT = new ThreadLocal<>();

    @Override
    public Request get()
    {
        return CURRENT.get();
    }

    public static void remove()
    {
        CURRENT.remove();
    }

    public static void set( final Request request )
    {
        CURRENT.set( request );
    }
}
