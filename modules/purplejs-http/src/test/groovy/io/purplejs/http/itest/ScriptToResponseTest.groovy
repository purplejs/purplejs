package io.purplejs.http.itest

import com.google.common.base.Charsets
import com.google.common.io.ByteSource
import com.google.common.net.MediaType
import io.purplejs.core.mock.MockResource
import io.purplejs.core.resource.Resource
import io.purplejs.core.resource.ResourcePath
import io.purplejs.http.Response
import io.purplejs.http.Status

class ScriptToResponseTest
    extends AbstractIntegrationTest
{
    private Response executeGet()
    {
        this.request.method = 'GET';
        return serve();
    }

    def byte[] toBytes( final String text )
    {
        return text.bytes;
    }

    def ByteSource toByteSource( final String text )
    {
        return ByteSource.wrap( text.bytes );
    }

    def Resource toResource( final String text )
    {
        return new MockResource( ResourcePath.from( '/my/resource.txt' ), text.bytes );
    }

    def "no return"()
    {
        setup:
        script( '''
            exports.get = function () {
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.status == Status.OK;
        res.body == null;
        res.contentType == MediaType.PLAIN_TEXT_UTF_8;
        res.value == null;
    }

    def "empty return"()
    {
        setup:
        script( '''
            exports.get = function () {
                return {};
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.status == Status.OK;
        res.body == null;
        res.contentType == MediaType.PLAIN_TEXT_UTF_8;
        res.value != null;
    }

    def "return status only"()
    {
        setup:
        script( '''
            exports.get = function () {
                return {
                    status: 201
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.status == Status.CREATED;
        res.body == null;
        res.contentType == MediaType.PLAIN_TEXT_UTF_8;
        res.value != null;
    }

    def "return all"()
    {
        setup:
        script( '''
            exports.get = function () {
                return {
                    status: 201,
                    body: 'text',
                    contentType: 'text/html',
                    headers: {
                        'X-Header-1': 'value1',
                        'X-Header-2': 'value2\'
                    }
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.status == Status.CREATED;
        toStringBody( res ) == 'text';
        res.contentType.toString() == 'text/html';
        res.headers.toString() == "{X-Header-2=value2, X-Header-1=value1}";
        res.value != null;
        res.cookies.isEmpty();
    }

    def "return redirect"()
    {
        setup:
        script( '''
            exports.get = function () {
                return {
                    redirect: 'http://foo.bar'
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.status == Status.SEE_OTHER;
        res.headers.toString() == "{Location=http://foo.bar}";
        res.value != null;
    }

    def "return text body"()
    {
        setup:
        script( '''
            exports.get = function () {
                return {
                    body: 'text'
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.body.asCharSource( Charsets.UTF_8 ).read() == 'text';
    }

    def "return function body"()
    {
        setup:
        script( '''
            exports.get = function () {
                return {
                    body: function () {
                        return 'text-in-function';
                    }
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        toStringBody( res ) == 'text-in-function';
    }

    def "return bytes body"()
    {
        setup:
        def bytes = toBytes( 'hello' );
        script( '''
            exports.get = function () {
                return {
                    body: t.toBytes('hello')
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.body.read() == bytes;
    }

    def "return byteSource body"()
    {
        setup:
        def bytes = toByteSource( 'hello' );
        script( '''
            exports.get = function () {
                return {
                    body: t.toByteSource('hello')
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.body.read() == bytes.read();
    }

    def "return resource body"()
    {
        setup:
        def resource = toResource( 'hello' );
        script( '''
            exports.get = function () {
                return {
                    body: t.toResource('hello')
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.body.read() == resource.bytes.read();
    }

    def "return null body"()
    {
        script( '''
            exports.get = function () {
                return {
                    body: undefined
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.body == null;
    }

    def "wrong return"()
    {
        script( '''
            exports.get = function () {
                return {
                    headers: []
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.getHeaders().toString() == '{}';
    }

    def "return json array"()
    {
        script( '''
            exports.get = function () {
                return {
                    body: [1, 2, 3, {
                        a: 1,
                        b: 2,
                        c: 3
                    }]
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.contentType.withoutParameters().toString() == 'application/json';
        toStringBody( res ) == '[1,2,3,{"a":1,"b":2,"c":3}]';
    }

    def "return json object"()
    {
        script( '''
            exports.get = function () {
                return {
                    body: {
                        a: 1,
                        b: 2,
                        c: 3,
                        d: [1, 2, 3]
                    }
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;
        res.contentType.withoutParameters().toString() == 'application/json';
        toStringBody( res ) == '{"a":1,"b":2,"c":3,"d":[1,2,3]}';
    }

    def "return cookies"()
    {
        script( '''
            exports.get = function () {
                return {
                    cookies: {
                        "cookie1": "value1",
                        "cookie2": {
                            "value": "value2",
                            "path": "/a/b",
                            "domain": "foo.com",
                            "comment": "a cookie",
                            "maxAge": 100,
                            "secure": true,
                            "httpOnly": true
                        },
                        "cookie3": {
                            "value": "value3"
                        },
                        "cookie4": undefined
                    }
                };
            };
        ''' );

        when:
        def res = executeGet();

        then:
        res != null;

        when:
        def cookies = res.cookies;

        then:
        cookies.size() == 3;

        when:
        def cookie = cookies.get( 0 );

        then:
        cookie.name == 'cookie1';
        cookie.value == 'value1';
        cookie.comment == null;
        cookie.path == null;
        cookie.domain == null;
        cookie.maxAge == -1;
        !cookie.isSecure();
        !cookie.isHttpOnly();

        when:
        cookie = cookies.get( 1 );

        then:
        cookie.name == 'cookie2';
        cookie.value == 'value2';
        cookie.comment == 'a cookie';
        cookie.path == '/a/b';
        cookie.domain == 'foo.com';
        cookie.maxAge == 100;
        cookie.isSecure();
        cookie.isHttpOnly();

        when:
        cookie = cookies.get( 2 );

        then:
        cookie.name == 'cookie3';
        cookie.value == 'value3';
        cookie.comment == null;
        cookie.path == null;
        cookie.domain == null;
        cookie.maxAge == -1;
        !cookie.isSecure();
        !cookie.isHttpOnly();
    }
}
