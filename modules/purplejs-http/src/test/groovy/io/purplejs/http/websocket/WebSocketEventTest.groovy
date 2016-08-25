package io.purplejs.http.websocket

import com.google.common.io.ByteSource
import spock.lang.Specification

class WebSocketEventTest
    extends Specification
{
    def "open event"()
    {
        setup:
        def session = Mock( WebSocketSession.class );

        when:
        def event = WebSocketEventBuilder.newBuilder().
            session( session ).
            openEvent().
            build();

        then:
        event != null;
        event.getSession() == session;
        event.getType() == WebSocketEventType.OPEN;
    }

    def "close event"()
    {
        setup:
        def session = Mock( WebSocketSession.class );

        when:
        def event = WebSocketEventBuilder.newBuilder().
            session( session ).
            closeEvent( 1001, 'my own reason' ).
            build();

        then:
        event != null;
        event.getSession() == session;
        event.getType() == WebSocketEventType.CLOSE;
        event.getCloseCode() == 1001;
        event.getCloseReason() == 'my own reason';
    }

    def "message event"()
    {
        setup:
        def session = Mock( WebSocketSession.class );

        when:
        def event = WebSocketEventBuilder.newBuilder().
            session( session ).
            messageEvent( 'my message' ).
            build();

        then:
        event != null;
        event.getSession() == session;
        event.getType() == WebSocketEventType.MESSAGE;
        event.getMessage() == 'my message';
    }

    def "binary event"()
    {
        setup:
        def session = Mock( WebSocketSession.class );
        def binary = ByteSource.wrap( 'hello'.bytes );

        when:
        def event = WebSocketEventBuilder.newBuilder().
            session( session ).
            binaryEvent( binary ).
            build();

        then:
        event != null;
        event.getSession() == session;
        event.getType() == WebSocketEventType.BINARY;
        event.getBinary() == binary;
    }

    def "error event"()
    {
        setup:
        def session = Mock( WebSocketSession.class );
        def error = new Throwable();

        when:
        def event = WebSocketEventBuilder.newBuilder().
            session( session ).
            errorEvent( error ).
            build();

        then:
        event != null;
        event.getSession() == session;
        event.getType() == WebSocketEventType.ERROR;
        event.getError() == error;
    }
}
