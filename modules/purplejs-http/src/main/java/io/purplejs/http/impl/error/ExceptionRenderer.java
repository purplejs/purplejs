package io.purplejs.http.impl.error;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Charsets;

import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.http.error.ErrorHandler;
import io.purplejs.http.error.ExceptionInfo;
import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourceException;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;

public final class ExceptionRenderer
{
    private final ErrorHandler handler;

    private final ResourceLoader resourceLoader;

    public ExceptionRenderer( final ErrorHandler handler, final ResourceLoader resourceLoader )
    {
        this.handler = handler != null ? handler : new DefaultErrorHandler();
        this.resourceLoader = resourceLoader;
    }

    public Response handle( final Request request, final Exception ex )
    {
        final ExceptionInfo info = toInfo( request, ex );
        return this.handler.handleException( info );
    }

    private ExceptionInfo toInfo( final Request request, final Exception ex )
    {
        final ExceptionInfoImpl info = new ExceptionInfoImpl();
        info.cause = ex;
        info.request = request;
        info.status = Status.INTERNAL_SERVER_ERROR;

        if ( ex instanceof ResourceException )
        {
            populate( info, (ResourceException) ex );
        }

        return info;
    }

    private void populate( final ExceptionInfoImpl info, final ResourceException ex )
    {
        info.path = ex.getResource();
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
