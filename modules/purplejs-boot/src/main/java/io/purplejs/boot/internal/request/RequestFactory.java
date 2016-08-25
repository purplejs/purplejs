package io.purplejs.boot.internal.request;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;

import io.purplejs.core.exception.ExceptionHelper;
import io.purplejs.http.Request;
import io.purplejs.http.RequestBuilder;
import io.purplejs.http.multipart.MultipartForm;

public final class RequestFactory
{
    private HttpServletRequest request;

    private boolean webSocket;

    public void setRequest( final HttpServletRequest request )
    {
        this.request = request;
    }

    public void setWebSocket( final boolean webSocket )
    {
        this.webSocket = webSocket;
    }

    public Request create()
    {
        final RequestBuilder builder = RequestBuilder.newBuilder();
        builder.method( this.request.getMethod() );
        builder.uri( this.request.getRequestURL().toString() );
        builder.webSocket( this.webSocket );
        builder.raw( this.request );
        builder.contentLength( this.request.getContentLength() );
        builder.contentType( this.request.getContentType() );

        builder.body( readBody() );

        addParameters( builder );
        addHeaders( builder );
        addCookies( builder );

        builder.multipart( readMultipart() );

        return builder.build();
    }

    private void addHeaders( final RequestBuilder builder )
    {
        final Enumeration<String> e = this.request.getHeaderNames();
        while ( e.hasMoreElements() )
        {
            final String key = e.nextElement();
            builder.header( key, this.request.getHeader( key ) );
        }
    }

    private void addParameters( final RequestBuilder builder )
    {
        final Enumeration<String> e = this.request.getParameterNames();
        while ( e.hasMoreElements() )
        {
            final String key = e.nextElement();
            for ( final String value : this.request.getParameterValues( key ) )
            {
                builder.parameter( key, value );
            }
        }
    }

    private void addCookies( final RequestBuilder builder )
    {
        final Cookie[] cookies = this.request.getCookies();
        if ( cookies == null )
        {
            return;
        }

        for ( final Cookie cookie : cookies )
        {
            builder.cookie( cookie.getName(), cookie.getValue() );
        }
    }

    private boolean isMultipartForm()
    {
        final String type = this.request.getContentType();
        return ( type != null ) && type.startsWith( "multipart/form-data" );
    }

    private ByteSource readBody()
    {
        if ( isMultipartForm() )
        {
            return null;
        }

        return readBody( this.request );
    }

    static ByteSource readBody( final HttpServletRequest req )
    {
        try
        {
            final byte[] data = ByteStreams.toByteArray( req.getInputStream() );
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

        return readMultipart( this.request );
    }

    static MultipartForm readMultipart( final HttpServletRequest req )
    {
        try
        {
            return new MultipartFormFactory().create( req.getParts() );
        }
        catch ( final Exception e )
        {
            throw ExceptionHelper.unchecked( e );
        }
    }
}
