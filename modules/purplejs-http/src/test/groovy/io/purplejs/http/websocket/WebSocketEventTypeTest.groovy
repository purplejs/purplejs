package io.purplejs.http.websocket

import spock.lang.Specification

class WebSocketEventTypeTest
    extends Specification
{
    def "test values"()
    {
        when:
        def values = WebSocketEventType.values();

        then:
        values.length == 5;
    }

    def "test valueOf"()
    {
        expect:
        def type = WebSocketEventType.valueOf( str );
        type == expected;

        where:
        str       | expected
        'OPEN'    | WebSocketEventType.OPEN
        'CLOSE'   | WebSocketEventType.CLOSE
        'MESSAGE' | WebSocketEventType.MESSAGE
        'BINARY'  | WebSocketEventType.BINARY
        'ERROR'   | WebSocketEventType.ERROR
    }
}
