package io.purplejs.http.itest

import com.google.common.io.ByteSource
import com.google.common.net.MediaType
import io.purplejs.http.mock.MockRequest

class HttpLibScriptTest
    extends AbstractIntegrationTest
{
    public MockRequest currentRequest()
    {
        return this.request;
    }

    def "test request"()
    {
        setup:
        script( '''
            var http = require('/lib/http');

            exports.get = function() {
                var req = http.request();
                t.assertEquals(req, t.currentRequest());
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
    }

    def "test isJsonBody"()
    {
        setup:
        this.request.method = 'POST';
        this.request.contentType = MediaType.create( 'application', 'json' );

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals(true, http.isJsonBody());
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
    }

    def "test isJsonBody, not json"()
    {
        setup:
        this.request.method = 'POST';
        this.request.contentType = MediaType.PLAIN_TEXT_UTF_8;

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals(false, http.isJsonBody());
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
    }

    def "test bodyAsText"()
    {
        setup:
        this.request.method = 'POST';
        this.request.body = ByteSource.wrap( 'hello'.bytes );

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals('hello', http.bodyAsText());
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
    }

    def "test bodyAsText, no body"()
    {
        setup:
        this.request.method = 'POST';

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals('', http.bodyAsText());
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
    }

    def "test bodyAsJson"()
    {
        setup:
        this.request.method = 'POST';
        this.request.body = ByteSource.wrap( '{"a":1}'.bytes );
        this.request.contentType = MediaType.create( 'application', 'json' );

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals('{"a":1}', JSON.stringify(http.bodyAsJson()));
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
    }

    def "test bodyAsJson, not json"()
    {
        setup:
        this.request.method = 'POST';

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals(undefined, http.bodyAsJson());
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
    }

    def "test bodyAsStream"()
    {
        setup:
        this.request.method = 'POST';
        this.request.body = ByteSource.wrap( 'hello'.bytes );

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals(5, http.bodyAsStream().size());
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
    }
}
