package io.purplejs.http.internal.response

import com.google.common.base.Charsets
import com.google.common.io.ByteSource
import com.google.common.net.MediaType
import io.purplejs.core.resource.Resource
import io.purplejs.core.value.ScriptExports
import io.purplejs.core.value.ScriptValue
import io.purplejs.http.Response
import io.purplejs.http.ScriptTestSupport
import io.purplejs.http.Status

class ScriptToResponseTest
    extends ScriptTestSupport
{
    private Response toResponse( final Object... args )
    {
        final ScriptExports exports = run( '/test.js' );
        final ScriptValue value = exports.executeMethod( 'run', args );
        return new ScriptToResponse().toResponse( value );
    }

    private void script( final String content )
    {
        file( '/test.js', content );
    }

    def "no return"()
    {
        setup:
        script( '''
            exports.run = function () {
            };
        ''' );

        when:
        def res = toResponse();

        then:
        res != null;
        res.status == Status.OK;
        res.body != null;
        res.contentType == MediaType.PLAIN_TEXT_UTF_8;
        res.value == null;
    }

    def "empty return"()
    {
        setup:
        script( '''
            exports.run = function () {
                return {};
            };
        ''' );

        when:
        def res = toResponse();

        then:
        res != null;
        res.status == Status.OK;
        res.body != null;
        res.contentType == MediaType.PLAIN_TEXT_UTF_8;
        res.value != null;
    }

    def "return status only"()
    {
        setup:
        script( '''
            exports.run = function () {
                return {
                    status: 201
                };
            };
        ''' );

        when:
        def res = toResponse();

        then:
        res != null;
        res.status == Status.CREATED;
        res.body != null;
        res.contentType == MediaType.PLAIN_TEXT_UTF_8;
        res.value != null;
    }

    def "return all"()
    {
        setup:
        script( '''
            exports.run = function () {
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
        def res = toResponse();

        then:
        res != null;
        res.status == Status.CREATED;
        res.body.asCharSource( Charsets.UTF_8 ).read() == 'text';
        res.contentType.toString() == 'text/html';
        res.headers.toString() == "{X-Header-2=value2, X-Header-1=value1}";
        res.value != null;
        res.cookies.isEmpty();
    }

    def "return redirect"()
    {
        setup:
        script( '''
            exports.run = function () {
                return {
                    redirect: 'http://foo.bar'
                };
            };
        ''' );

        when:
        def res = toResponse();

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
            exports.run = function () {
                return {
                    body: 'text'
                };
            };
        ''' );

        when:
        def res = toResponse();

        then:
        res != null;
        res.body.asCharSource( Charsets.UTF_8 ).read() == 'text';
    }

    def "return function body"()
    {
        setup:
        script( '''
            exports.run = function () {
                return {
                    body: function () {
                        return 'text-in-function';
                    }
                };
            };
        ''' );

        when:
        def res = toResponse();

        then:
        res != null;
        res.body.asCharSource( Charsets.UTF_8 ).read() == 'text-in-function';
    }

    def "return bytes body"()
    {
        setup:
        def bytes = ByteSource.wrap( "bytes".bytes );
        script( '''
            exports.run = function (obj) {
                return {
                    body: obj.read()
                };
            };
        ''' );

        when:
        def res = toResponse( bytes );

        then:
        res != null;
        res.body.read() == bytes.read();
    }

    def "return byteSource body"()
    {
        setup:
        def bytes = ByteSource.wrap( "bytes".bytes );
        script( '''
            exports.run = function (obj) {
                return {
                    body: obj
                };
            };
        ''' );

        when:
        def res = toResponse( bytes );

        then:
        res != null;
        res.body == bytes;
    }

    def "return resource body"()
    {
        setup:
        def resource = Mock( Resource.class );
        def bytes = ByteSource.wrap( "bytes".bytes );
        resource.getBytes() >> bytes;

        script( '''
            exports.run = function (obj) {
                return {
                    body: obj
                };
            };
        ''' );

        when:
        def res = toResponse( resource );

        then:
        res != null;
        res.body == bytes;
    }

    def "return null body"()
    {
        script( '''
            exports.run = function () {
                return {
                    body: undefined
                };
            };
        ''' );

        when:
        def res = toResponse();

        then:
        res != null;
        res.body == ByteSource.empty();
    }

    def "wrong return"()
    {
        script( '''
            exports.run = function () {
                return {
                    headers: []
                };
            };
        ''' );

        when:
        def res = toResponse();

        then:
        res != null;
        res.getHeaders().toString() == '{}';
    }

    def "return json array"()
    {
        script( '''
            exports.run = function () {
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
        def res = toResponse();

        then:
        res != null;
        res.contentType.withoutParameters().toString() == 'application/json';
        res.body.asCharSource( Charsets.UTF_8 ).read() == '[1,2,3,{"a":1,"b":2,"c":3}]';
    }

    def "return json object"()
    {
        script( '''
            exports.run = function () {
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
        def res = toResponse();

        then:
        res != null;
        res.contentType.withoutParameters().toString() == 'application/json';
        res.body.asCharSource( Charsets.UTF_8 ).read() == '{"a":1,"b":2,"c":3,"d":[1,2,3]}';
    }

    def "return cookies"()
    {
        script( '''
            exports.run = function () {
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
        def res = toResponse();

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
