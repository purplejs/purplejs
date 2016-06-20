package io.purplejs.http.impl.error;

import io.purplejs.http.error.ErrorHandler;

public final class ExceptionRenderer
{
    private final ErrorHandler handler;

    public ExceptionRenderer( final ErrorHandler handler )
    {
        this.handler = handler;
    }


}
