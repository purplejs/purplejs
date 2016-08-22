package io.purplejs.http.internal.error;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Charsets;

import io.purplejs.core.exception.ProblemException;
import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.http.error.ErrorHandler;
import io.purplejs.http.error.ErrorInfo;

public final class ErrorRendererImpl
    implements ErrorRenderer
{
    private final ErrorHandler handler;

    private final ResourceLoader resourceLoader;

    public ErrorRendererImpl( final ErrorHandler handler, final ResourceLoader resourceLoader )
    {
        this.handler = handler != null ? handler : new DefaultErrorHandler();
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Response handle( final Request request, final Throwable ex )
    {
        final ErrorInfo info = toInfo( request, ex );
        return this.handler.handle( info );
    }

    @Override
    public Response handle( final Request request, final Status status )
    {
        final ErrorInfo info = toInfo( request, status );
        return this.handler.handle( info );
    }

    private ErrorInfo toInfo( final Request request, final Throwable ex )
    {
        final ErrorInfoImpl info = new ErrorInfoImpl();
        info.cause = ex;
        info.request = request;
        info.status = Status.INTERNAL_SERVER_ERROR;

        if ( ex instanceof ProblemException )
        {
            populate( info, (ProblemException) ex );
        }

        return info;
    }

    private ErrorInfo toInfo( final Request request, final Status status )
    {
        final ErrorInfoImpl info = new ErrorInfoImpl();
        info.cause = null;
        info.request = request;
        info.status = status;
        return info;
    }

    private void populate( final ErrorInfoImpl info, final ProblemException ex )
    {
        info.path = ex.getPath();

        final Resource resource = this.resourceLoader.loadOrNull( info.path );
        info.lines = loadLines( resource );
    }

    private List<String> loadLines( final Resource resource )
    {
        if ( resource == null )
        {
            return Collections.emptyList();
        }

        try
        {
            return resource.getBytes().asCharSource( Charsets.UTF_8 ).readLines();
        }
        catch ( final Exception e )
        {
            return Collections.emptyList();
        }
    }
}
