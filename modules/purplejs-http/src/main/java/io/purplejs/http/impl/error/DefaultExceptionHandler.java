package io.purplejs.http.impl.error;

import io.purplejs.http.Response;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.error.ExceptionInfo;

public final class DefaultExceptionHandler
    implements ExceptionHandler
{
    @Override
    public Response handle( final ExceptionInfo ex )
    {
        return null;
    }
}
