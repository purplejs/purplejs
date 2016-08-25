package io.purplejs.http.internal.handler

import io.purplejs.core.value.ScriptExports
import io.purplejs.http.websocket.WebSocketEvent
import spock.lang.Specification

class HandleEventCommandTest
    extends Specification
{
    def WebSocketEvent event;

    def HandleEventCommand command;

    def ScriptExports exports;

    def setup()
    {
        this.event = Mock( WebSocketEvent.class );
        this.command = new HandleEventCommand( this.event );
        this.exports = Mock( ScriptExports.class );
    }

    def "method exist"()
    {
        setup:
        this.exports.hasMethod( 'webSocketEvent' ) >> true;

        when:
        def res = this.command.apply( this.exports );

        then:
        res;
        1 * this.exports.executeMethod( 'webSocketEvent', _ as JsonWebSocketEvent );
    }

    def "method not found"()
    {
        when:
        def res = this.command.apply( this.exports );

        then:
        !res;
    }
}
