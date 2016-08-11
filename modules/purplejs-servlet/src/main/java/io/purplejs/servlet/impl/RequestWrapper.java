package io.purplejs.servlet.impl;

import java.net.URI;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.net.MediaType;

import io.purplejs.http.Headers;
import io.purplejs.http.MultipartForm;
import io.purplejs.http.Parameters;
import io.purplejs.http.Request;
import io.purplejs.exception.ExceptionHelper;

public final class RequestWrapper
    implements Request
{
    private final HttpServletRequest wrapped;

    private final Headers headers;

    private final Parameters parameters;

    private ByteSource body;

    public RequestWrapper( final HttpServletRequest wrapped )
    {
        this.wrapped = wrapped;
        this.headers = createHeaders( this.wrapped );
        this.parameters = createParameters( this.wrapped );
    }

    @Override
    public String getMethod()
    {
        return this.wrapped.getMethod();
    }

    @Override
    public URI getUri()
    {
        return URI.create( this.wrapped.getRequestURL().toString() );
    }

    @Override
    public Parameters getParameters()
    {
        return this.parameters;
    }

    @Override
    public Headers getHeaders()
    {
        return this.headers;
    }

    @Override
    public MediaType getContentType()
    {
        final String value = this.wrapped.getContentType();
        return value != null ? MediaType.parse( value ).withoutParameters() : null;
    }

    @Override
    public long getContentLength()
    {
        return this.wrapped.getContentLength();
    }

    @Override
    public ByteSource getBody()
    {
        if ( getContentLength() <= 0 )
        {
            return ByteSource.empty();
        }

        if ( this.body == null )
        {
            try
            {
                this.body = ByteSource.wrap( ByteStreams.toByteArray( this.wrapped.getInputStream() ) );
            }
            catch ( final Exception e )
            {
                throw ExceptionHelper.unchecked( e );
            }
        }

        return this.body;
    }

    @Override
    public MultipartForm getMultipart()
    {
        try
        {
            return new MultipartFormImpl( this.wrapped.getParts() );
        }
        catch ( final Exception e )
        {
            throw ExceptionHelper.unchecked( e );
        }
    }

    @Override
    public Object getRaw()
    {
        return this.wrapped;
    }

    private static Headers createHeaders( final HttpServletRequest req )
    {
        final Headers headers = new Headers();
        final Enumeration<String> e = req.getHeaderNames();

        while ( e.hasMoreElements() )
        {
            final String key = e.nextElement();
            headers.set( key, req.getHeader( key ) );
        }

        return headers;
    }

    private static Parameters createParameters( final HttpServletRequest req )
    {
        final Parameters params = new Parameters();
        final Enumeration<String> e = req.getParameterNames();

        while ( e.hasMoreElements() )
        {
            final String key = e.nextElement();
            for ( final String value : req.getParameterValues( key ) )
            {
                params.put( key, value );
            }
        }

        return params;
    }
}
