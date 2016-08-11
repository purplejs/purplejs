package io.purplejs.servlet.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.google.common.base.Charsets;

import io.purplejs.http.Headers;
import io.purplejs.http.Parameters;
import io.purplejs.http.Request;

import static org.junit.Assert.*;

public class RequestWrapperTest
{
    private MockHttpServletRequest request;

    @Before
    public void setUp()
    {
        this.request = new MockHttpServletRequest();
    }

    private Request createWrapper()
    {
        return new RequestWrapper( this.request );
    }

    @Test
    public void getMethod()
    {
        this.request.setMethod( "GET" );

        final Request wrapper = createWrapper();
        assertEquals( "GET", wrapper.getMethod() );
    }

    @Test
    public void getMultipart()
        throws Exception
    {
        this.request.setContentType( "multipart/form-data" );

        final Request wrapper = createWrapper();
        assertNotNull( wrapper.getMultipart() );
        assertEquals( 0, wrapper.getBody().size() );
    }

    @Test
    public void getMultipart_notMultipart()
    {
        final Request wrapper = createWrapper();
        assertNull( wrapper.getMultipart() );
    }

    @Test
    public void getHeaders()
    {
        this.request.addHeader( "X-Header1", "Value1" );
        this.request.addHeader( "X-Header2", "Value2" );

        final Request wrapper = createWrapper();
        final Headers headers = wrapper.getHeaders();
        assertNotNull( headers );
        assertEquals( 2, headers.size() );
        assertEquals( "{X-Header1=Value1, X-Header2=Value2}", headers.toString() );
    }

    @Test
    public void getRaw()
    {
        final Request wrapper = createWrapper();
        assertSame( this.request, wrapper.getRaw() );
    }

    @Test
    public void getUri()
    {
        this.request.setServerPort( 8080 );
        this.request.setRequestURI( "/test" );

        final Request wrapper = createWrapper();
        assertEquals( "http://localhost:8080/test", wrapper.getUri().toString() );
    }

    @Test
    public void getParameters()
    {
        this.request.addParameter( "a", "1" );
        this.request.addParameter( "b", "2" );
        this.request.addParameter( "b", "3" );

        final Request wrapper = createWrapper();
        final Parameters parameters = wrapper.getParameters();
        assertNotNull( parameters );
        assertEquals( 3, parameters.size() );
        assertEquals( "{a=[1], b=[2, 3]}", parameters.toString() );
    }

    @Test
    public void getContentType()
    {
        final Request wrapper1 = createWrapper();
        assertNull( wrapper1.getContentType() );

        this.request.setContentType( "text/plain" );
        final Request wrapper2 = createWrapper();
        assertEquals( "text/plain", wrapper2.getContentType().toString() );
    }

    @Test
    public void getContentLength()
    {
        this.request.setContent( "hello".getBytes( Charsets.UTF_8 ) );
        final Request wrapper = createWrapper();
        assertEquals( 5, wrapper.getContentLength() );
    }

    @Test
    public void getBody()
        throws Exception
    {
        this.request.setContent( null );
        final Request wrapper1 = createWrapper();
        assertEquals( 0, wrapper1.getBody().size() );

        this.request.setContent( "hello".getBytes( Charsets.UTF_8 ) );

        final Request wrapper2 = createWrapper();
        assertEquals( "hello", wrapper2.getBody().asCharSource( Charsets.UTF_8 ).read() );
        assertEquals( "hello", wrapper2.getBody().asCharSource( Charsets.UTF_8 ).read() );
    }
}
