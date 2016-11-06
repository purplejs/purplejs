package io.purplejs.websocket.internal

import io.purplejs.core.EngineBuilder
import io.purplejs.websocket.handler.WebSocketHandlerFactory
import spock.lang.Specification

class WebSocketModuleTest
    extends Specification
{
    def "test module"()
    {
        setup:
        def engine = EngineBuilder.newBuilder().
            module( new WebSocketModule() ).
            build()

        when:
        def factory = engine.getInstance( WebSocketHandlerFactory.class );

        then:
        factory != null;
    }
}
