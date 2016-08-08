package io.purplejs.servlet.impl;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.Cookie;
import io.purplejs.http.Response;
import io.purplejs.http.ResponseBuilder;
import io.purplejs.http.Status;

import static org.junit.Assert.*;

public class ResponseSerializerTest
{
    private Response newResponse()
    {
        return ResponseBuilder.newBuilder().
            status( Status.OK ).
            contentType( MediaType.PLAIN_TEXT_UTF_8 ).
            header( "X-Header1", "Value1" ).
            header( "X-Header2", "Value2" ).
            body( ByteSource.wrap( "hello".getBytes( Charsets.UTF_8 ) ) ).
            cookie( new Cookie( "cookie1" ) ).
            cookie( new Cookie( "cookie2" ) ).
            build();

    }

    @Test
    public void serialize()
        throws Exception
    {
        final MockHttpServletResponse response = new MockHttpServletResponse();

        final ResponseSerializer serializer = new ResponseSerializer( response );
        serializer.serialize( newResponse() );

        assertEquals( "text/plain; charset=utf-8", response.getContentType() );
        assertEquals( "[Content-Type, X-Header1, X-Header2]", response.getHeaderNames().toString() );
        assertEquals( "Value1", response.getHeader( "X-Header1" ) );
        assertEquals( "Value2", response.getHeader( "X-Header2" ) );
        assertEquals( "hello", response.getContentAsString() );

        assertNotNull( response.getCookie( "cookie1" ) );
        assertNotNull( response.getCookie( "cookie2" ) );
    }

    @Test
    public void translateCookie()
    {
        final Cookie cookie = new Cookie( "name" );

        javax.servlet.http.Cookie result = ResponseSerializer.translateCookie( cookie );
        assertEquals( "name", result.getName() );

        cookie.setValue( "value" );
        cookie.setPath( "path" );
        cookie.setDomain( "domain" );
        cookie.setComment( "comment" );
        cookie.setSecure( true );
        cookie.setHttpOnly( true );
        cookie.setMaxAge( 3600 );

        result = ResponseSerializer.translateCookie( cookie );
        assertEquals( "value", result.getValue() );
        assertEquals( "path", result.getPath() );
        assertEquals( "domain", result.getDomain() );
        assertEquals( "comment", result.getComment() );
        assertEquals( true, result.getSecure() );
        assertEquals( true, result.isHttpOnly() );
        assertEquals( 3600, result.getMaxAge() );
    }
}
