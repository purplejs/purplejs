package io.purplejs.http.itest

import io.purplejs.http.Status

class BasicHandlingTest
    extends AbstractIntegrationTest
{
    def "no methods"()
    {
        setup:
        script( '''
        ''' );

        when:
        def res = serve();

        then:
        res != null;
        res.status == Status.METHOD_NOT_ALLOWED;
        res.body == null;
    }

    def "get method"()
    {
        setup:
        script( '''
            exports.get = function(req) {
                return {
                    body: {}
                };
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
        res.contentType.subtype() == 'json';
        toStringBody( res ) == '{}';
    }

    def "service method"()
    {
        setup:
        script( '''
            exports.service = function(req) {
                return {
                    body: 'hello'
                };
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
        res.contentType.type() == 'text';
        toStringBody( res ) == 'hello';
    }

    def "post method"()
    {
        setup:
        script( '''
            exports.post = function(req) {
                return {
                    status: 201
                };
            };
        ''' );

        when:
        this.request.method = 'POST';
        def res = serve();

        then:
        res != null;
        res.status == Status.CREATED;
        res.body == null;
    }

    def "head with get method"()
    {
        setup:
        script( '''
            exports.get = function(req) {
                return {
                    body: 'hello'
                };
            };
        ''' );

        when:
        this.request.method >> 'HEAD';
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
    }

    def "controlled error"()
    {
        setup:
        script( '''
            exports.get = function(req) {
                return {
                    status: 404,
                    body: {}
                };
            };
        ''' );

        when:
        this.request.method >> 'GET';
        def res = serve();

        then:
        res != null;
        res.status == Status.NOT_FOUND;
    }
}
