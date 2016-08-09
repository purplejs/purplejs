package io.purplejs.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;

import io.purplejs.Engine;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.ResponseBuilder;
import io.purplejs.http.Status;
import io.purplejs.http.handler.HttpHandler;

import static org.junit.Assert.*;

public class ScriptServletTest
{
    private MockServletConfig config;

    @Before
    public void setUp()
        throws Exception
    {
        this.config = new MockServletConfig();
        this.config.addInitParameter( "resource", "/a/b.js" );
    }

    private ScriptServlet newServlet()
        throws Exception
    {
        final ScriptServlet servlet = new ScriptServlet();
        servlet.init( this.config );
        return servlet;
    }

    @Test
    public void init()
        throws Exception
    {
        final ScriptServlet servlet = newServlet();
        assertNotNull( servlet.engine );
        assertNotNull( servlet.handler );
        assertEquals( "{}", servlet.engine.getConfig().toString() );
    }

    @Test
    public void init_config()
        throws Exception
    {
        this.config.addInitParameter( "config.a", "1" );
        this.config.addInitParameter( "config.b", "2" );

        final ScriptServlet servlet = newServlet();
        assertEquals( "{a=1, b=2}", servlet.engine.getConfig().toString() );
    }

    @Test
    public void service()
        throws Exception
    {
        final Response response = ResponseBuilder.newBuilder().
            status( Status.OK ).
            body( ByteSource.wrap( "hello".getBytes( Charsets.UTF_8 ) ) ).
            build();

        final ScriptServlet servlet = newServlet();
        servlet.handler = Mockito.mock( HttpHandler.class );
        Mockito.when( servlet.handler.serve( Mockito.any() ) ).thenReturn( response );

        final MockHttpServletRequest req = new MockHttpServletRequest();
        req.setMethod( "GET" );

        final MockHttpServletResponse res = new MockHttpServletResponse();

        servlet.service( req, res );

        assertEquals( 200, res.getStatus() );
        assertEquals( "hello", res.getContentAsString() );

        final ArgumentCaptor<Request> requestArg = ArgumentCaptor.forClass( Request.class );
        Mockito.verify( servlet.handler, Mockito.times( 1 ) ).serve( requestArg.capture() );

        final Request request = requestArg.getValue();
        assertNotNull( request );
        assertEquals( "GET", request.getMethod() );
    }

    @Test
    public void dispose()
        throws Exception
    {
        final ScriptServlet servlet = newServlet();
        servlet.engine = Mockito.mock( Engine.class );

        servlet.destroy();
        Mockito.verify( servlet.engine, Mockito.times( 1 ) ).dispose();
    }
}
