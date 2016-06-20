package io.purplejs.http.impl.response;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.Engine;
import io.purplejs.EngineBuilder;
import io.purplejs.http.Cookie;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;
import io.purplejs.value.ScriptValue;

import static org.junit.Assert.*;

public class ScriptToResponseTest
{
    private ScriptExports exports;

    @Before
    public void setup()
    {
        final Engine engine = EngineBuilder.newBuilder().
            build();
        this.exports = engine.require( ResourcePath.from( "/response/response-test.js" ) );
    }

    private Response toResponse( final String name, final Object... args )
    {
        final ScriptValue value = this.exports.executeMethod( name, args );
        return new ScriptToResponse().toResponse( value );
    }

    @Test
    public void empty()
    {
        final Response response = toResponse( "empty" );
        assertNotNull( response );
        assertEquals( Status.OK, response.getStatus() );
        assertNotNull( response.getBody() );
        assertEquals( MediaType.OCTET_STREAM, response.getContentType() );
        assertNull( response.getValue() );
    }

    @Test
    public void statusOnly()
    {
        final Response response = toResponse( "statusOnly" );
        assertNotNull( response );
        assertEquals( Status.CREATED, response.getStatus() );
        assertNotNull( response.getValue() );
    }

    @Test
    public void all()
        throws Exception
    {
        final Response response = toResponse( "all" );
        assertNotNull( response );
        assertEquals( Status.OK, response.getStatus() );
        assertEquals( "text", response.getBody().asCharSource( Charsets.UTF_8 ).read() );
        assertEquals( "text/plain", response.getContentType().toString() );
        assertEquals( "{X-Header-2=value2, X-Header-1=value1}", response.getHeaders().asMap().toString() );
        assertNotNull( response.getValue() );
        assertEquals( 0, response.getCookies().size() );
    }

    @Test
    public void redirect()
    {
        final Response response = toResponse( "redirect" );
        assertNotNull( response );
        assertEquals( Status.SEE_OTHER, response.getStatus() );
        assertEquals( "{Location=http://foo.bar}", response.getHeaders().asMap().toString() );
        assertNotNull( response.getValue() );
    }

    @Test
    public void textBody()
        throws Exception
    {
        final Response response = toResponse( "textBody" );
        assertEquals( "text", response.getBody().asCharSource( Charsets.UTF_8 ).read() );
    }

    @Test
    public void functionBody()
        throws Exception
    {
        final Response response = toResponse( "functionBody" );
        assertEquals( "text-in-function", response.getBody().asCharSource( Charsets.UTF_8 ).read() );
    }

    @Test
    public void bytesBody()
        throws Exception
    {
        final byte[] bytes = "bytes".getBytes();
        final Response response = toResponse( "argBody", new Object[]{bytes} );
        assertArrayEquals( bytes, response.getBody().read() );
    }

    @Test
    public void byteSourceBody()
        throws Exception
    {
        final ByteSource byteSource = ByteSource.wrap( "bytes".getBytes() );
        final Response response = toResponse( "argBody", byteSource );
        assertEquals( byteSource, response.getBody() );
    }

    @Test
    public void resourceBody()
        throws Exception
    {
        final Resource resource = Mockito.mock( Resource.class );
        final ByteSource byteSource = ByteSource.wrap( "bytes".getBytes() );
        Mockito.when( resource.getBytes() ).thenReturn( byteSource );

        final Response response = toResponse( "argBody", resource );
        assertEquals( byteSource, response.getBody() );
    }

    @Test
    public void nullBody()
        throws Exception
    {
        final Response response = toResponse( "argBody", new Object[]{null} );
        assertEquals( ByteSource.empty(), response.getBody() );
    }

    @Test
    public void wrong()
    {
        final Response response = toResponse( "wrong" );
        assertNotNull( response );
        assertEquals( "{}", response.getHeaders().asMap().toString() );
    }

    @Test
    public void jsonArrayBody()
        throws Exception
    {
        final Response response = toResponse( "jsonArrayBody" );
        assertEquals( "[1,2,3,{\"a\":1,\"b\":2,\"c\":3}]", response.getBody().asCharSource( Charsets.UTF_8 ).read() );
    }

    @Test
    public void jsonObjectBody()
        throws Exception
    {
        final Response response = toResponse( "jsonObjectBody" );
        assertEquals( "{\"a\":1,\"b\":2,\"c\":3,\"d\":[1,2,3]}", response.getBody().asCharSource( Charsets.UTF_8 ).read() );
    }

    @Test
    public void cookies()
    {
        final Response response = toResponse( "cookies" );
        final List<Cookie> cookies = response.getCookies();

        assertEquals( 2, cookies.size() );

        final Cookie cookie1 = cookies.get( 0 );
        assertEquals( "cookie1", cookie1.getName() );
        assertEquals( "value1", cookie1.getValue() );
        assertNull( cookie1.getComment() );
        assertNull( cookie1.getPath() );
        assertNull( cookie1.getDomain() );
        assertEquals( -1, cookie1.getMaxAge() );
        assertEquals( false, cookie1.isSecure() );
        assertEquals( false, cookie1.isHttpOnly() );

        final Cookie cookie2 = cookies.get( 1 );
        assertEquals( "cookie2", cookie2.getName() );
        assertEquals( "value2", cookie2.getValue() );
        assertEquals( "a cookie", cookie2.getComment() );
        assertEquals( "/a/b", cookie2.getPath() );
        assertEquals( "foo.com", cookie2.getDomain() );
        assertEquals( 100, cookie2.getMaxAge() );
        assertEquals( true, cookie2.isSecure() );
        assertEquals( true, cookie2.isHttpOnly() );
    }
}
