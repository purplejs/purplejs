package io.purplejs.http

import com.google.common.io.ByteSource
import com.google.common.net.MediaType
import io.purplejs.core.value.ScriptValue
import spock.lang.Specification

class ResponseBuilderTest
    extends Specification
{
    def "builder"()
    {
        setup:
        def body = ByteSource.empty();
        def rawValue = Mock( ScriptValue.class );
        def cookie1 = new Cookie( "cookie1" );
        def cookie2 = new Cookie( "cookie2" );

        when:
        def response = ResponseBuilder.newBuilder().
            status( Status.OK ).
            header( "X-Header1", "Value1" ).
            header( "X-Header2", "Value2" ).
            body( body ).
            contentType( MediaType.HTML_UTF_8 ).
            value( rawValue ).
            cookie( cookie1 ).
            cookie( cookie2 ).
            build();

        then:
        response.status == Status.OK;
        response.body == body;
        response.contentType == MediaType.HTML_UTF_8;
        response.value == rawValue;

        when:
        def headers = response.getHeaders();

        then:
        headers.getOrDefault( "X-Header1", "" ) == "Value1";
        headers.getOrDefault( "X-Header2", "" ) == "Value2";

        when:
        def cookies = response.getCookies();

        then:
        cookies.size() == 2;
        cookies.get( 0 ) == cookie1;
        cookies.get( 1 ) == cookie2;
    }
}
