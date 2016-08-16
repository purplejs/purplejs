package io.purplejs.http;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.core.value.ScriptValue;

import static org.junit.Assert.*;

public class ResponseBuilderTest
{
    @Test
    public void builder()
    {
        final ByteSource body = ByteSource.empty();
        final ScriptValue rawValue = Mockito.mock( ScriptValue.class );
        final Cookie cookie1 = new Cookie( "cookie1" );
        final Cookie cookie2 = new Cookie( "cookie2" );

        final ResponseBuilder builder = ResponseBuilder.newBuilder();
        builder.status( Status.OK );
        builder.header( "X-Header1", "Value1" );
        builder.header( "X-Header2", "Value2" );
        builder.body( body );
        builder.contentType( MediaType.HTML_UTF_8 );
        builder.value( rawValue );
        builder.cookie( cookie1 );
        builder.cookie( cookie2 );

        final Response response = builder.build();
        assertEquals( Status.OK, response.getStatus() );
        assertEquals( body, response.getBody() );
        assertEquals( MediaType.HTML_UTF_8, response.getContentType() );
        assertEquals( rawValue, response.getValue() );

        final Headers headers = response.getHeaders();
        assertEquals( "Value1", headers.getOrDefault( "X-Header1", "" ) );
        assertEquals( "Value2", headers.getOrDefault( "X-Header2", "" ) );

        final List<Cookie> cookies = response.getCookies();
        assertEquals( 2, cookies.size() );
        assertSame( cookie1, cookies.get( 0 ) );
        assertSame( cookie2, cookies.get( 1 ) );
    }
}
