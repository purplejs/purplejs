package io.purplejs.servlet.internal

import com.google.common.base.Charsets
import io.purplejs.http.Request
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class RequestWrapperTest
    extends Specification
{
    def MockHttpServletRequest request;

    def setup()
    {
        this.request = new MockHttpServletRequest();
    }

    private Request createWrapper()
    {
        return new RequestWrapper( this.request );
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
}
