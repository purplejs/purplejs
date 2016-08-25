package io.purplejs.boot.internal.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.common.io.ByteSource;

import io.purplejs.http.Cookie;
import io.purplejs.http.Headers;
import io.purplejs.http.Response;

public final class ResponseSerializer
{
    private final HttpServletResponse to;

    public ResponseSerializer( final HttpServletResponse to )
    {
        this.to = to;
    }

    public void serialize( final Response from )
        throws IOException
    {
        this.to.setStatus( from.getStatus().getCode() );
        this.to.setContentType( from.getContentType().toString() );

        serializeHeaders( from.getHeaders() );
        serializeCookies( from.getCookies().values() );

        getBody( from ).copyTo( this.to.getOutputStream() );
    }

    private ByteSource getBody( final Response from )
    {
        final ByteSource body = from.getBody();
        return body != null ? body : ByteSource.empty();
    }

    private void serializeHeaders( final Headers headers )
    {
        headers.forEach( this.to::addHeader );
    }

    private void serializeCookies( final Iterable<Cookie> cookies )
    {
        cookies.forEach( this::serializeCookie );
    }

    private void serializeCookie( final Cookie cookie )
    {
        this.to.addCookie( translateCookie( cookie ) );
    }

    static javax.servlet.http.Cookie translateCookie( final Cookie cookie )
    {
        final javax.servlet.http.Cookie result = new javax.servlet.http.Cookie( cookie.getName(), cookie.getValue() );
        result.setPath( cookie.getPath() );
        result.setComment( cookie.getComment() );
        result.setMaxAge( cookie.getMaxAge() );
        result.setHttpOnly( cookie.isHttpOnly() );
        result.setSecure( cookie.isSecure() );

        if ( cookie.getDomain() != null )
        {
            result.setDomain( cookie.getDomain() );
        }

        return result;
    }
}
