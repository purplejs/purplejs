package io.purplejs.http.handler

import com.google.common.base.Charsets
import io.purplejs.core.EngineBuilder
import io.purplejs.core.mock.MockResource
import io.purplejs.core.mock.MockResourceLoader
import io.purplejs.core.resource.ResourcePath
import io.purplejs.http.*
import spock.lang.Specification

class HttpHandlerTest
    extends Specification
{
    def HttpHandlerFactory handlerFactory;

    def Request request;

    def Headers headers;

    def Parameters params;

    def MockResourceLoader resourceLoader;

    def setup()
    {
        this.resourceLoader = new MockResourceLoader();

        def engine = EngineBuilder.newBuilder().
            resourceLoader( this.resourceLoader ).
            build();

        this.handlerFactory = engine.getInstance( HttpHandlerFactory.class );

        this.headers = new Headers();
        this.params = new Parameters();

        this.request = Mock( Request.class );
        this.request.headers >> this.headers;
        this.request.parameters >> this.params;
        this.request.uri >> new URI( 'http://localhost:8080/a/b' );
    }

    private MockResource file( final String content )
    {
        def path = ResourcePath.from( '/test.js' );
        return this.resourceLoader.addResource( path, content.trim() );
    }

    private Response serve()
    {
        def path = ResourcePath.from( '/test.js' );
        def handler = this.handlerFactory.newHandler( path );
        return handler.serve( this.request );
    }

    def "no methods"()
    {
        setup:
        file( '''
        ''' );

        when:
        this.request.method >> 'GET';
        def res = serve();

        then:
        res != null;
        res.status == Status.METHOD_NOT_ALLOWED;
        res.body == null;
    }

    def "get method"()
    {
        setup:
        file( '''
            exports.get = function(req) {
                return {
                    body: {}
                };
            };
        ''' );

        when:
        this.request.method >> 'GET';
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
        res.contentType.subtype() == 'json';
        res.body.asCharSource( Charsets.UTF_8 ).read() == '{}';
    }

    def "service method"()
    {
        setup:
        file( '''
            exports.service = function(req) {
                return {
                    body: 'hello'
                };
            };
        ''' );

        when:
        this.request.method >> 'GET';
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
        res.contentType.type() == 'text';
        res.body.asCharSource( Charsets.UTF_8 ).read() == 'hello';
    }

    def "post method"()
    {
        setup:
        file( '''
            exports.post = function(req) {
                return {
                    status: 201
                };
            };
        ''' );

        when:
        this.request.method >> 'POST';
        def res = serve();

        then:
        res != null;
        res.status == Status.CREATED;
        res.body.asCharSource( Charsets.UTF_8 ).read() == '';
    }

    def "head with get method"()
    {
        setup:
        file( '''
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
        file( '''
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
