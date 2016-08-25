package io.purplejs.boot.internal.websocket

import com.google.common.base.Charsets
import io.purplejs.http.handler.HttpHandler
import io.purplejs.http.websocket.WebSocketConfig
import io.purplejs.http.websocket.WebSocketEvent
import io.purplejs.http.websocket.WebSocketEventType
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class WebSocketHandlerTest
    extends Specification
{
    def WebSocketHandler handler;

    def HttpHandler httpHandler;

    def WebSocketConfig config;

    def Session session;

    def setup()
    {
        this.httpHandler = Mock( HttpHandler.class );
        this.config = Mock( WebSocketConfig.class );
        this.handler = new WebSocketHandler( this.httpHandler, this.config );
        this.session = Mock( Session.class );
    }

    def "onWebSocketBinary"()
    {
        when:
        this.handler.onWebSocketBinary( 'hello'.bytes, 0, 5 );

        then:
        1 * this.httpHandler.handleEvent( { WebSocketEvent event ->
            event.type == WebSocketEventType.BINARY;
            event.binary.asCharSource( Charsets.UTF_8 ).read() == 'hello';
                                          } as WebSocketEvent );
    }

    def "onWebSocketText"()
    {
        when:
        this.handler.onWebSocketText( 'hello' );

        then:
        1 * this.httpHandler.handleEvent( { WebSocketEvent event ->
            event.type == WebSocketEventType.MESSAGE;
            event.message == 'hello';
                                          } as WebSocketEvent );
    }

    def "onWebSocketClose"()
    {
        when:
        this.handler.onWebSocketClose( 1001, 'no reason' );

        then:
        1 * this.httpHandler.handleEvent( { WebSocketEvent event ->
            event.type == WebSocketEventType.CLOSE;
            event.closeCode == 1001;
            event.closeReason == 'no reason';
                                          } as WebSocketEvent );
    }

    def "onWebSocketConnect"()
    {
        setup:
        this.config.timeout >> 1000;

        when:
        this.handler.onWebSocketConnect( this.session );

        then:
        1 * this.session.setIdleTimeout( 1000 );
        1 * this.httpHandler.handleEvent( { WebSocketEvent event ->
            event.type == WebSocketEventType.OPEN;
            event.session != null;
                                          } as WebSocketEvent );
    }

    def "onWebSocketError"()
    {
        setup:
        def Throwable cause = new Throwable();

        when:
        this.handler.onWebSocketError( cause );

        then:
        1 * this.httpHandler.handleEvent( { WebSocketEvent event ->
            event.type == WebSocketEventType.ERROR;
            event.error == cause;
                                          } as WebSocketEvent );
    }

    def "createWebSocket, no sub-protocols"()
    {
        setup:
        def req = newServletUpgradeRequest( '' );
        def res = newServletUpgradeResponse();

        this.config.subProtocols >> [];

        when:
        def socket = this.handler.createWebSocket( req, res );

        then:
        socket != null;
    }

    def "createWebSocket, protocols not match"()
    {
        setup:
        def req = newServletUpgradeRequest( 'text,binary' );
        def res = newServletUpgradeResponse();

        this.config.subProtocols >> ['other'];

        when:
        def socket = this.handler.createWebSocket( req, res );

        then:
        socket == null;
    }

    def "createWebSocket, protocols match"()
    {
        setup:
        def req = newServletUpgradeRequest( 'text,binary' );
        def res = newServletUpgradeResponse();

        this.config.subProtocols >> ['text'];

        when:
        def socket = this.handler.createWebSocket( req, res );

        then:
        socket != null;
        res.getAcceptedSubProtocol() == 'text';
    }

    private static ServletUpgradeRequest newServletUpgradeRequest( final String protocols )
    {
        final MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader( "Sec-WebSocket-Protocol", protocols );
        return new ServletUpgradeRequest( req );
    }

    private static ServletUpgradeResponse newServletUpgradeResponse()
    {
        return new ServletUpgradeResponse( new MockHttpServletResponse() );
    }
}
