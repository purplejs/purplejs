package io.purplejs.servlet.impl;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.Headers;
import io.purplejs.http.Response;
import io.purplejs.http.Status;

import static org.junit.Assert.*;

public class ResponseSerializerTest
{
    private Response newResponse()
    {
        final Response response = Mockito.mock( Response.class );
        Mockito.when( response.getStatus() ).thenReturn( Status.OK );
        Mockito.when( response.getContentType() ).thenReturn( MediaType.PLAIN_TEXT_UTF_8 );

        final Headers headers = new Headers();
        headers.set( "X-Header1", "Value1" );
        headers.set( "X-Header2", "Value2" );

        Mockito.when( response.getHeaders() ).thenReturn( headers );
        Mockito.when( response.getBody() ).thenReturn( ByteSource.wrap( "hello".getBytes( Charsets.UTF_8 ) ) );
        return response;
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
    }
}
