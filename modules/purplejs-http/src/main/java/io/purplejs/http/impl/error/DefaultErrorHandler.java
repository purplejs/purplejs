package io.purplejs.http.impl.error;

import io.purplejs.http.Response;
import io.purplejs.http.error.ErrorHandler;
import io.purplejs.http.error.ExceptionInfo;

final class DefaultErrorHandler
    implements ErrorHandler
{
    @Override
    public Response handleException( final ExceptionInfo ex )
    {
        return null;
    }
}
