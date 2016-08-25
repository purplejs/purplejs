package io.purplejs.boot.internal.response

import com.google.common.base.Charsets
import com.google.common.io.ByteSource
import com.google.common.net.MediaType
import io.purplejs.http.Cookie
import io.purplejs.http.ResponseBuilder
import io.purplejs.http.Status
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class ResponseSerializerTest
    extends Specification
{
    def "serialize"()
    {
        setup:
        def response = new MockHttpServletResponse();
        def serializer = new ResponseSerializer( response );
        def from = ResponseBuilder.newBuilder().
            status( Status.OK ).
            contentType( MediaType.PLAIN_TEXT_UTF_8 ).
            header( "X-Header1", "Value1" ).
            header( "X-Header2", "Value2" ).
            body( ByteSource.wrap( "hello".getBytes( Charsets.UTF_8 ) ) ).
            cookie( new Cookie( "cookie1" ) ).
            cookie( new Cookie( "cookie2" ) ).
            build();

        when:
        serializer.serialize( from );

        then:
        response.contentType == 'text/plain; charset=utf-8';
        response.headerNames.toString() == '[Content-Type, X-Header1, X-Header2]';
        response.getHeader( 'X-Header1' ) == 'Value1';
        response.getHeader( 'X-Header2' ) == 'Value2';
        response.contentAsString == 'hello';
        response.getCookie( 'cookie1' ) != null;
        response.getCookie( 'cookie2' ) != null;
    }

    def "serialize null body"()
    {
        setup:
        def response = new MockHttpServletResponse();
        def serializer = new ResponseSerializer( response );
        def from = ResponseBuilder.newBuilder().
            status( Status.OK ).
            build();

        when:
        serializer.serialize( from );

        then:
        response.contentAsString == '';
    }

    def "translateCookie"()
    {
        setup:
        def cookie = new Cookie( "name" );

        when:
        def result = ResponseSerializer.translateCookie( cookie );

        then:
        result.name == 'name';

        when:
        cookie.setValue( "value" );
        cookie.setPath( "path" );
        cookie.setDomain( "domain" );
        cookie.setComment( "comment" );
        cookie.setSecure( true );
        cookie.setHttpOnly( true );
        cookie.setMaxAge( 3600 );
        result = ResponseSerializer.translateCookie( cookie );

        then:
        result.value == 'value';
        result.path == 'path';
        result.domain == 'domain';
        result.comment == 'comment';
        result.secure;
        result.httpOnly;
        result.maxAge == 3600;
    }
}
