package io.purplejs.http.impl.error;

import io.purplejs.http.Response;
import io.purplejs.http.error.ErrorHandler;
import io.purplejs.resource.ResourceLoader;

public final class DefaultErrorHandler
    implements ErrorHandler
{
    private ResourceLoader resourceLoader;

    @Override
    public Response handleException( final Exception ex )
    {
        final ExceptionInfo info = toExceptionInfo( ex );
        return null;
    }

    private ExceptionInfo toExceptionInfo( final Exception ex )
    {
        return null;
    }
}
