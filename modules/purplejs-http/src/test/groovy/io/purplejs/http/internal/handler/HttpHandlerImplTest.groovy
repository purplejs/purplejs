package io.purplejs.http.internal.handler

import com.google.common.io.ByteSource
import io.purplejs.core.Engine
import io.purplejs.core.resource.ResourcePath
import io.purplejs.http.ResponseBuilder
import io.purplejs.http.Status
import io.purplejs.http.internal.error.ErrorRenderer
import io.purplejs.http.mock.MockRequest
import spock.lang.Specification

class HttpHandlerImplTest
    extends Specification
{
    def HttpHandlerImpl handler;

    def setup()
    {
        this.handler = new HttpHandlerImpl();
        this.handler.engine = Mock( Engine.class );
        this.handler.errorRenderer = Mock( ErrorRenderer.class );
        this.handler.resource = ResourcePath.from( '/test.js' );
    }

    def "errorIfNeeded"()
    {
        setup:
        def req = new MockRequest();
        def body = ByteSource.wrap( 'hello'.bytes );

        def mockResult = ResponseBuilder.newBuilder().
            status( status ).
            body( body ).
            build();

        this.handler.errorRenderer.handle( req, status ) >> mockResult;

        def res = ResponseBuilder.newBuilder().
            status( status ).
            build();

        when:
        def result = this.handler.errorIfNeeded( req, res );

        then:
        result == mockResult;
        result.status == status;
        result.body == body;

        where:
        status << [Status.NOT_FOUND, Status.INTERNAL_SERVER_ERROR]
    }

    def "errorIfNeeded, not needed"()
    {
        setup:
        def req = new MockRequest();
        def res = ResponseBuilder.newBuilder().
            status( status ).
            body( body ).
            build();

        when:
        def result = this.handler.errorIfNeeded( req, res );

        then:
        result == res;
        result.status == status;
        result.body == body;

        where:
        status                       | body
        Status.OK                    | null
        Status.NOT_FOUND             | ByteSource.wrap( 'hello'.bytes )
        Status.INTERNAL_SERVER_ERROR | ByteSource.wrap( 'hello'.bytes )
    }

    def "handleException"()
    {
        setup:
        def req = new MockRequest();
        def cause = new IOException();

        def mockResult = ResponseBuilder.newBuilder().
            build();

        this.handler.errorRenderer.handle( req, cause ) >> mockResult;

        when:
        def result = this.handler.handleException( req, cause );

        then:
        result == mockResult;
    }
}
