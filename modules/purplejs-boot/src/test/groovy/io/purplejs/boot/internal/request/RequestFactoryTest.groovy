package io.purplejs.boot.internal.request

import com.google.common.base.Charsets
import io.purplejs.http.Request
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

class RequestFactoryTest
    extends Specification
{
    def MockHttpServletRequest request;

    def boolean webSocket;

    def setup()
    {
        this.request = new MockHttpServletRequest();
    }

    private Request createWrapper()
    {
        final RequestFactory factory = new RequestFactory();
        factory.request = this.request;
        factory.webSocket = this.webSocket;
        return factory.create();
    }

    def "getMethod"()
    {
        setup:
        this.request.method = 'GET';

        when:
        def wrapper = createWrapper();

        then:
        wrapper.method == 'GET';
    }

    def "getMultipart"()
    {
        setup:
        this.request.contentType = 'multipart/form-data';

        when:
        def wrapper = createWrapper();

        then:
        wrapper.multipart != null;
        wrapper.body.size() == 0;
    }

    def "getMultipart not multipart"()
    {
        when:
        def wrapper = createWrapper();

        then:
        wrapper.multipart == null;
    }

    def "getHeaders"()
    {
        setup:
        this.request.addHeader( "X-Header1", "Value1" );
        this.request.addHeader( "X-Header2", "Value2" );

        when:
        def wrapper = createWrapper();
        def headers = wrapper.getHeaders();

        then:
        headers != null;
        headers.size() == 2;
        headers.toString() == '{X-Header1=Value1, X-Header2=Value2}';
    }

    def "getRaw"()
    {
        when:
        def wrapper = createWrapper();

        then:
        wrapper.getRaw() == this.request;
    }

    def "getUri"()
    {
        setup:
        this.request.serverPort = 8080;
        this.request.requestURI = "/test";

        when:
        def wrapper = createWrapper();

        then:
        wrapper.getUri().toString() == 'http://localhost:8080/test';
    }

    def "getParameters"()
    {
        setup:
        this.request.addParameter( "a", "1" );
        this.request.addParameter( "b", "2" );
        this.request.addParameter( "b", "3" );

        when:
        def wrapper = createWrapper();
        def params = wrapper.getParameters();

        then:
        params != null;
        params.size() == 3;
        params.toString() == '{a=[1], b=[2, 3]}';
    }

    def "getContentType"()
    {
        when:
        def wrapper = createWrapper();

        then:
        wrapper.contentType == null;

        when:
        this.request.contentType = 'text/plain';
        wrapper = createWrapper();

        then:
        wrapper.contentType.toString() == 'text/plain';
    }

    def "getContentLength"()
    {
        setup:
        this.request.content = 'hello'.bytes;

        when:
        def wrapper = createWrapper();

        then:
        wrapper.contentLength == 5;
    }

    def "getBody"()
    {
        when:
        this.request.content = null;
        def wrapper = createWrapper();

        then:
        wrapper.body != null;
        wrapper.body.size() == 0;

        when:
        this.request.content = 'hello'.bytes;
        wrapper = createWrapper();

        then:
        wrapper.body != null;
        wrapper.body.asCharSource( Charsets.UTF_8 ).read() == 'hello';
    }

    def "readBody failure"()
    {
        setup:
        def req = Mock( HttpServletRequest.class );
        req.getInputStream() >> { throw new IOException() };

        when:
        RequestFactory.readBody( req );

        then:
        thrown IOException;
    }

    def "readMultipart failure"()
    {
        setup:
        def req = Mock( HttpServletRequest.class );
        req.getParts() >> { throw new IOException() };

        when:
        RequestFactory.readMultipart( req );

        then:
        thrown IOException;
    }

    def "getCookies"()
    {
        setup:
        def cookie1 = new Cookie( 'cookie1', 'value1' );
        def cookie2 = new Cookie( 'cookie2', 'value2' );
        this.request.setCookies( cookie1, cookie2 );

        when:
        def wrapper = createWrapper();
        def cookies = wrapper.getCookies();

        then:
        cookies != null;
        cookies.size() == 2;
        cookies.toString() == '[cookie1:value1, cookie2:value2]';
    }
}
