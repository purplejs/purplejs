package io.purplejs.http

import com.google.common.io.ByteSource
import com.google.common.net.MediaType
import io.purplejs.core.value.ScriptValue
import io.purplejs.http.websocket.WebSocketConfig
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
        def webSocket = Mock( WebSocketConfig.class );

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
            webSocket( webSocket ).
            build();

        then:
        response.status == Status.OK;
        response.body == body;
        response.contentType == MediaType.HTML_UTF_8;
        response.value == rawValue;
        response.webSocket == webSocket;

        when:
        def headers = response.getHeaders();

        then:
        headers.getOrDefault( "X-Header1", "" ) == "Value1";
        headers.getOrDefault( "X-Header2", "" ) == "Value2";

        when:
        def cookies = response.getCookies();

        then:
        cookies.size() == 2;
        cookies.get( 'cookie1' ) == cookie1;
        cookies.get( 'cookie2' ) == cookie2;
    }
}
