package io.purplejs.http.internal.error;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Charsets;

import io.purplejs.core.exception.ProblemException;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.error.ExceptionInfo;
import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

public final class ExceptionRenderer
{
    private final ExceptionHandler handler;

    private final ResourceLoader resourceLoader;

    public ExceptionRenderer( final ExceptionHandler handler, final ResourceLoader resourceLoader )
    {
        this.handler = handler != null ? handler : new DefaultExceptionHandler();
        this.resourceLoader = resourceLoader;
    }

    public Response handle( final Request request, final Exception ex )
    {
        final ExceptionInfo info = toInfo( request, ex );
        return this.handler.handle( info );
    }

    public Response handle( final Request request, final Status status )
    {
        final ExceptionInfo info = toInfo( request, status );
        return this.handler.handle( info );
    }

    private ExceptionInfo toInfo( final Request request, final Exception ex )
    {
        final ExceptionInfoImpl info = new ExceptionInfoImpl();
        info.cause = ex;
        info.request = request;
        info.status = Status.INTERNAL_SERVER_ERROR;

        if ( ex instanceof ProblemException )
        {
            populate( info, (ProblemException) ex );
        }

        return info;
    }

    private ExceptionInfo toInfo( final Request request, final Status status )
    {
        final ExceptionInfoImpl info = new ExceptionInfoImpl();
        info.cause = null;
        info.request = request;
        info.status = status;
        return info;
    }

    private void populate( final ExceptionInfoImpl info, final ProblemException ex )
    {
        info.path = ex.getPath();
        info.resource = loadResourceOrNull( info.path );
        info.lines = loadLines( info.resource );
    }

    private Resource loadResourceOrNull( final ResourcePath path )
    {
        try
        {
            return this.resourceLoader.load( path );
        }
        catch ( final Exception e )
        {
            return null;
        }
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
