package io.purplejs.servlet.impl;

import java.net.URI;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.net.MediaType;

import io.purplejs.exception.ExceptionHelper;
import io.purplejs.http.Headers;
import io.purplejs.http.MultipartForm;
import io.purplejs.http.Parameters;
import io.purplejs.http.Request;

public final class RequestWrapper
    implements Request
{
    private final static MediaType MULTIPART_FORM = MediaType.create( "multipart", "form-data" );

    private final HttpServletRequest wrapped;

    private final Headers headers;

    private final Parameters parameters;

    private final ByteSource body;

    private final MultipartForm multipart;

    private final MediaType contentType;

    private final long contentLength;

    public RequestWrapper( final HttpServletRequest wrapped )
    {
        this.wrapped = wrapped;
        this.contentLength = this.wrapped.getContentLength();

        final String value = this.wrapped.getContentType();
        this.contentType = value != null ? MediaType.parse( value ).withoutParameters() : null;

        this.body = readBody();

        this.parameters = createParameters( this.wrapped );
        this.headers = createHeaders( this.wrapped );

        this.multipart = readMultipart();
    }

    private boolean isMultipartForm()
    {
        return ( this.contentType != null ) && this.contentType.is( MULTIPART_FORM );
    }

    private ByteSource readBody()
    {
        if ( isMultipartForm() )
        {
            return ByteSource.empty();
        }

        try
        {
            final byte[] data = ByteStreams.toByteArray( this.wrapped.getInputStream() );
            return ByteSource.wrap( data );
        }
        catch ( final Exception e )
        {
            throw ExceptionHelper.unchecked( e );
        }
    }

    private MultipartForm readMultipart()
    {
        if ( !isMultipartForm() )
        {
            return null;
        }

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
        return this.contentType;
    }

    @Override
    public long getContentLength()
    {
        return this.contentLength;
    }

    @Override
    public ByteSource getBody()
    {
        return this.body;
    }

    @Override
    public MultipartForm getMultipart()
    {
        return this.multipart;
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
