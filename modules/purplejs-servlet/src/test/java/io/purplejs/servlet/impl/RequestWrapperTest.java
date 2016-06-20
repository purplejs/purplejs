package io.purplejs.servlet.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import io.purplejs.http.Headers;

import static org.junit.Assert.*;

public class RequestWrapperTest
{
    private MockHttpServletRequest request;

    private RequestWrapper wrapper;

    @Before
    public void setup()
    {
        this.request = new MockHttpServletRequest();
        this.wrapper = new RequestWrapper( this.request );
    }

    @Test
    public void getMethod()
    {
        this.request.setMethod( "GET" );
        assertEquals( "GET", this.wrapper.getMethod() );
    }

    @Test
    public void getAttributes()
    {
        assertNotNull( this.wrapper.getAttributes() );
    }

    @Test
    public void getMultipart()
    {
        assertNotNull( this.wrapper.getMultipart() );
    }

    @Test
    public void getHeaders()
    {
        this.request.addHeader( "X-Header1", "Value1" );
        this.request.addHeader( "X-Header2", "Value2" );

        final Headers headers = this.wrapper.getHeaders();
        assertNotNull( headers );
    }

    @Test
    public void getRaw()
    {
        assertSame( this.request, this.wrapper.getRaw() );
    }
}
