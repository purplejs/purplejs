package io.purplejs.http.internal.handler

import com.google.common.io.ByteSource
import io.purplejs.core.Engine
import io.purplejs.core.exception.ProblemException
import io.purplejs.core.resource.ResourcePath
import io.purplejs.core.value.ScriptExports
import io.purplejs.http.RequestBuilder
import io.purplejs.http.ResponseBuilder
import io.purplejs.http.Status
import io.purplejs.http.internal.error.ErrorRenderer
import io.purplejs.http.internal.websocket.WebSocketRegistry
import io.purplejs.http.websocket.WebSocketEventBuilder
import io.purplejs.http.websocket.WebSocketSession
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
        this.handler.webSocketRegistry = Mock( WebSocketRegistry.class );

        final ScriptExports exports = Mock( ScriptExports.class );
        this.handler.engine.require( this.handler.resource ) >> exports;
    }

    def "errorIfNeeded"()
    {
        setup:
        def req = RequestBuilder.newBuilder().build();
        def body = ByteSource.wrap( 'hello'.bytes );

        def mockResult = ResponseBuilder.newBuilder().
            status( status ).
            body( body ).
            build();

        this.handler.errorRenderer.handle( req, status, null ) >> mockResult;

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
        def req = RequestBuilder.newBuilder().build();
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

    def "errorIfNeeded, webSocket"()
    {
        setup:
        def req = RequestBuilder.newBuilder().
            webSocket( true ).
            build();

        def res = ResponseBuilder.newBuilder().
            status( Status.OK ).
            build();

        when:
        def result = this.handler.errorIfNeeded( req, res );

        then:
        res == result;
    }

    def "handleException"()
    {
        setup:
        def req = RequestBuilder.newBuilder().build();
        def cause = new IOException();

        def mockResult = ResponseBuilder.newBuilder().
            build();

        this.handler.errorRenderer.handle( req, Status.INTERNAL_SERVER_ERROR, cause ) >> mockResult;

        when:
        def result = this.handler.handleException( req, cause );

        then:
        result == mockResult;
    }

    def "handleException - not handled"()
    {
        setup:
        def req = RequestBuilder.newBuilder().build();
        def cause = ProblemException.newBuilder().build();

        def mockResult = ResponseBuilder.newBuilder().
            build();

        this.handler.errorRenderer.handle( req, Status.INTERNAL_SERVER_ERROR, cause ) >> mockResult;

        when:
        def result = this.handler.handleException( req, cause );

        then:
        result == mockResult;
    }

    def "handleException, webSocket"()
    {
        setup:
        def req = RequestBuilder.newBuilder().
            webSocket( true ).
            build();

        def cause = new IOException();

        when:
        def result = this.handler.handleException( req, cause );

        then:
        result == null;
    }

    def "handleEvent, open event"()
    {
        setup:
        def session = Mock( WebSocketSession.class );
        def event = WebSocketEventBuilder.newBuilder().
            session( session ).
            openEvent().
            build();

        when:
        def result = this.handler.handleEvent( event );

        then:
        !result;
        1 * this.handler.webSocketRegistry.add( session );
    }

    def "handleEvent, close event"()
    {
        setup:
        def session = Mock( WebSocketSession.class );
        def event = WebSocketEventBuilder.newBuilder().
            session( session ).
            closeEvent( 1001, 'every reason' ).
            build();

        when:
        def result = this.handler.handleEvent( event );

        then:
        !result;
        1 * this.handler.webSocketRegistry.remove( session );
    }

    def "handleEvent, message event"()
    {
        setup:
        def session = Mock( WebSocketSession.class );
        def event = WebSocketEventBuilder.newBuilder().
            session( session ).
            messageEvent( 'my message' ).
            build();

        when:
        def result = this.handler.handleEvent( event );

        then:
        !result;
    }
}
