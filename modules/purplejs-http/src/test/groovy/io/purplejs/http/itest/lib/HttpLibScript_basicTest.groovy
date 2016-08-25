package io.purplejs.http.itest.lib

import com.google.common.net.MediaType
import io.purplejs.http.Request
import io.purplejs.http.Status
import io.purplejs.http.itest.AbstractHttpITest

class HttpLibScript_basicTest
    extends AbstractHttpITest
{
    public Request currentRequest()
    {
        return this.requestBuilder.build();
    }

    def "test getRequest"()
    {
        setup:
        script( '''
            var http = require('/lib/http');

            exports.get = function() {
                var req = http.getRequest();
                t.assertEquals(true, req != null);
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
    }

    def "test isJsonBody"()
    {
        setup:
        this.requestBuilder.method( 'POST' );
        this.requestBuilder.contentType( 'application/json' );

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
        res.status == Status.OK;
    }

    def "test isJsonBody, not json"()
    {
        setup:
        this.requestBuilder.method( 'POST' );
        this.requestBuilder.contentType( MediaType.PLAIN_TEXT_UTF_8 );

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
        res.status == Status.OK;
    }

    def "test bodyAsText"()
    {
        setup:
        this.requestBuilder.method( 'POST' );
        this.requestBuilder.body( 'hello' );

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
        res.status == Status.OK;
    }

    def "test bodyAsText, no body"()
    {
        setup:
        this.requestBuilder.method( 'POST' );

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
        res.status == Status.OK;
    }

    def "test bodyAsJson"()
    {
        setup:
        this.requestBuilder.method( 'POST' );
        this.requestBuilder.body( '{"a":1}' );
        this.requestBuilder.contentType( 'application/json' );

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
        res.status == Status.OK;
    }

    def "test bodyAsJson, not json"()
    {
        setup:
        this.requestBuilder.method( 'POST' );

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
        res.status == Status.OK;
    }

    def "test bodyAsStream"()
    {
        setup:
        this.requestBuilder.method( 'POST' );
        this.requestBuilder.body( 'hello' );

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
        res.status == Status.OK;
    }
}
