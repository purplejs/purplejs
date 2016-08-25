package io.purplejs.router.itest

import io.purplejs.http.Status
import io.purplejs.http.itest.AbstractHttpITest

class RouterLibTest
    extends AbstractHttpITest
{
    def "no routes"()
    {
        setup:
        script( '''
            var router = require('/lib/router')();

            exports.service = function(req) {
                return router.dispatch(req);
            };
        ''' );

        when:
        def res = serve();

        then:
        res.status == Status.NOT_FOUND;
    }

    def "simple routes"()
    {
        setup:
        script( '''
            var router = require('/lib/router')();

            router.get('/a/b', function(req) {
                return {
                    body: 'GET /a/b'
                };
            });

            router.all('/a/b', function(req) {
                return {
                    body: '* /a/b'
                };
            });

            exports.service = function(req) {
                return router.dispatch(req);
            };
        ''' );

        when:
        this.requestBuilder.method( method );
        this.requestBuilder.uri( 'http://localhost:8080' + path );
        def res = serve();

        then:
        res.status == status;
        toStringBody( res ) == result;

        where:
        method   | path   | status           | result
        'GET'    | '/a'   | Status.NOT_FOUND | null
        'GET'    | '/a/b' | Status.OK        | 'GET /a/b'
        'DELETE' | '/a/b' | Status.OK        | '* /a/b'
    }

    def "route with path params"()
    {
        setup:
        script( '''
            var router = require('/lib/router')();

            router.get('/a/{id}', function(req) {
                return {
                    body: 'GET /a/' + req.pathParams.id
                };
            });

            exports.service = function(req) {
                return router.dispatch(req);
            };
        ''' );

        when:
        this.requestBuilder.method( 'GET' );
        this.requestBuilder.uri( 'http://localhost:8080/a/12' );
        def res = serve();

        then:
        res.status == Status.OK;
        toStringBody( res ) == 'GET /a/12';
    }

    def "only filters"()
    {
        setup:
        script( '''
            var router = require('/lib/router')();

            router.filter(function(req, next) {
                var res = next(req);
                res.body = 'filter1,' + res.body;
                return res;
            });

            router.filter(function(req, next) {
                return {
                    body: 'filter2'
                };
            });

            exports.service = function(req) {
                return router.dispatch(req);
            };
        ''' );

        when:
        this.requestBuilder.method( 'GET' );
        this.requestBuilder.uri( 'http://localhost:8080/a/12' );
        def res = serve();

        then:
        res.status == Status.OK;
        toStringBody( res ) == 'filter1,filter2';
    }

    def "filter and route"()
    {
        setup:
        script( '''
            var router = require('/lib/router')();

            router.filter(function(req, next) {
                var res = next(req);
                res.body = 'filter,' + res.body;
                return res;
            });

            router.get('/a', function(req) {
                return {
                    body: 'route'
                };
            });

            exports.service = function(req) {
                return router.dispatch(req);
            };
        ''' );

        when:
        this.requestBuilder.method( 'GET' );
        this.requestBuilder.uri( 'http://localhost:8080/a' );
        def res = serve();

        then:
        res.status == Status.OK;
        toStringBody( res ) == 'filter,route';
    }
}
