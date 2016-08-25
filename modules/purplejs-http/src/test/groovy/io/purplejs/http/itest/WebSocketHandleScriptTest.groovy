package io.purplejs.http.itest

import com.google.common.io.ByteSource
import io.purplejs.http.websocket.WebSocketEventBuilder
import io.purplejs.http.websocket.WebSocketSession

class WebSocketHandleScriptTest
    extends AbstractIntegrationTest
{
    def String eventJson;

    def WebSocketEventBuilder eventBuilder;

    def setup()
    {
        this.eventJson = null;

        final WebSocketSession session = Mock( WebSocketSession.class );
        session.id >> '1';
        session.group >> 'somewhere';

        this.eventBuilder = WebSocketEventBuilder.newBuilder().
            session( session );
    }

    public boolean handleEvent()
    {
        script( '''
            exports.webSocketEvent = function(event) {
                t.eventJson = JSON.stringify(event);
            };
        ''' );

        return handleEvent( this.eventBuilder.build() );
    }

    def "open event"()
    {
        setup:
        this.eventBuilder.openEvent();

        when:
        def res = handleEvent();

        then:
        res;
        prettifyJson( this.eventJson ) == prettifyJson( '''
            {
              "type": "open",
              "session": {
                "id": "1",
                "group": "somewhere"
              }
            }
        ''' );
    }

    def "error event"()
    {
        setup:
        def cause = new RuntimeException( "some error" );
        this.eventBuilder.errorEvent( cause );

        when:
        def res = handleEvent();

        then:
        res;
        prettifyJson( this.eventJson ) == prettifyJson( '''
            {
              "type": "error",
              "error": "some error",
              "session": {
                "id": "1",
                "group": "somewhere"
              }
            }
        ''' );
    }

    def "close event"()
    {
        setup:
        this.eventBuilder.closeEvent( 1001, 'some reason' );

        when:
        def res = handleEvent();

        then:
        res;
        prettifyJson( this.eventJson ) == prettifyJson( '''
            {
              "type": "close",
              "closeCode": 1001,
              "closeReason": "some reason",
              "session": {
                "id": "1",
                "group": "somewhere"
              }
            }
        ''' );
    }

    def "message event"()
    {
        setup:
        this.eventBuilder.messageEvent( 'text message' );

        when:
        def res = handleEvent();

        then:
        res;
        prettifyJson( this.eventJson ) == prettifyJson( '''
            {
              "type": "message",
              "message": "text message",
              "session": {
                "id": "1",
                "group": "somewhere"
              }
            }
        ''' );
    }

    def "binary event"()
    {
        setup:
        this.eventBuilder.binaryEvent( ByteSource.wrap( 'hello'.bytes ) );

        when:
        def res = handleEvent();

        then:
        res;
        prettifyJson( this.eventJson ) == prettifyJson( '''
            {
              "type": "binary",
              "session": {
                "id": "1",
                "group": "somewhere"
              }
            }
        ''' );
    }
}
