package io.purplejs.boot.internal.servlet

import com.google.common.base.Charsets
import com.google.common.io.ByteSource
import io.purplejs.core.Engine
import io.purplejs.core.EngineBuilder
import io.purplejs.http.Request
import io.purplejs.http.Response
import io.purplejs.http.ResponseBuilder
import io.purplejs.http.Status
import io.purplejs.http.handler.HttpHandler
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockServletConfig

// TODO: Need to convert this to an integration test with jetty.
class ScriptServletTest
// extends Specification
{
    def MockServletConfig config;

    def setup()
    {
        this.config = new MockServletConfig();
    }

    private ScriptServlet newServlet()
    {
        final ScriptServlet servlet = new ScriptServlet();
        servlet.engine = EngineBuilder.newBuilder().build();
        servlet.init( this.config );
        return servlet;
    }

    def "test init"()
    {
        when:
        def servlet = newServlet();

        then:
        servlet.handler != null;
    }

    def "test dispose"()
    {
        setup:
        def servlet = newServlet();
        servlet.engine = Mock( Engine.class );

        when:
        servlet.destroy();

        then:
        1 * servlet.engine.dispose();
    }

    def "test service"()
    {
        setup:
        def response = ResponseBuilder.newBuilder().
            status( Status.OK ).
            body( ByteSource.wrap( "hello".getBytes( Charsets.UTF_8 ) ) ).
            build();

        def servlet = newServlet();
        servlet.handler = Mock( HttpHandler.class );
        servlet.handler.serve( _ as Request ) >> response;
        servlet.handler.errorIfNeeded( _ as Request, _ as Response ) >> response;

        def servletRequest = new MockHttpServletRequest();
        servletRequest.setMethod( "GET" );

        def servletResponse = new MockHttpServletResponse();

        when:
        servlet.service( servletRequest, servletResponse );

        then:
        servletResponse.status == 200;
        servletResponse.contentAsString == "hello";
    }
}
