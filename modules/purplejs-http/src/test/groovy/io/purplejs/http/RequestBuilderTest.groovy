package io.purplejs.http

import com.google.common.base.Charsets
import com.google.common.io.ByteSource
import com.google.common.net.MediaType
import io.purplejs.http.multipart.MultipartForm
import spock.lang.Specification

class RequestBuilderTest
    extends Specification
{
    def "default values"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            build();

        then:
        req != null;
        req.method == 'GET';
        req.contentLength == 0;
        req.contentType == null;
        req.cookies.isEmpty();
        req.parameters.isEmpty();
        req.headers.isEmpty();
        req.multipart == null;
        !req.webSocket;
        req.raw == null;
        req.body != null;
        req.uri.toString() == 'http://localhost:8080';
    }

    def "set uri"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            uri( 'http://foo.bar' ).
            build();

        then:
        req != null;
        req.uri.toString() == 'http://foo.bar';

        when:
        req = RequestBuilder.newBuilder().
            uri( URI.create( 'http://foo.bar' ) ).
            build();

        then:
        req != null;
        req.uri.toString() == 'http://foo.bar';
    }

    def "set body"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            body( 'hello' ).
            build();

        then:
        req != null;
        req.body.asCharSource( Charsets.UTF_8 ).read() == 'hello';

        when:
        req = RequestBuilder.newBuilder().
            body( ByteSource.wrap( 'hello'.bytes ) ).
            build();

        then:
        req != null;
        req.body.asCharSource( Charsets.UTF_8 ).read() == 'hello';

        when:
        req = RequestBuilder.newBuilder().
            body( (String) null ).
            build();

        then:
        req != null;
        req.body == ByteSource.empty();
    }

    def "set method"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            method( 'post' ).
            build();

        then:
        req.method == 'POST';

        when:
        req = RequestBuilder.newBuilder().
            method( 'DELETE' ).
            build();

        then:
        req.method == 'DELETE';
    }

    def "set parameter"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            parameter( 'a', '1' ).
            parameter( 'b', '2' ).
            build();

        then:
        req.parameters.toString() == '{a=[1], b=[2]}';
    }

    def "set header"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            header( 'a', '1' ).
            header( 'b', '2' ).
            build();

        then:
        req.headers.toString() == '{a=1, b=2}';
    }

    def "set cookie"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            cookie( 'a', '1' ).
            cookie( 'b', '2' ).
            build();

        then:
        req.cookies.toString() == '[a:1, b:2]';
    }

    def "set contentType"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            contentType( 'application/json' ).
            build();

        then:
        req.contentType.toString() == 'application/json';

        when:
        req = RequestBuilder.newBuilder().
            contentType( MediaType.PLAIN_TEXT_UTF_8 ).
            build();

        then:
        req.contentType.toString() == 'text/plain; charset=utf-8';

        when:
        req = RequestBuilder.newBuilder().
            contentType( (String) null ).
            build();

        then:
        req != null;
        req.contentType == null;
    }

    def "set contentLength"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            contentLength( 100 ).
            build();

        then:
        req.contentLength == 100;
    }

    def "set webSocket"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            webSocket( true ).
            build();

        then:
        req.webSocket;
    }

    def "set multipart"()
    {
        when:
        def req = RequestBuilder.newBuilder().
            multipart( new MultipartForm() ).
            build();

        then:
        req.multipart != null;
    }

    def "set raw"()
    {
        when:
        def raw = new Object();
        def req = RequestBuilder.newBuilder().
            raw( raw ).
            build();

        then:
        req.raw == raw;
    }
}
