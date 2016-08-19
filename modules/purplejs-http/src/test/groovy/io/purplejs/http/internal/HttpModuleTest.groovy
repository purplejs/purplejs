package io.purplejs.http.internal

import io.purplejs.core.EngineBuilder
import io.purplejs.http.handler.HttpHandlerFactory
import spock.lang.Specification

class HttpModuleTest
    extends Specification
{
    def "test module"()
    {
        setup:
        def engine = EngineBuilder.newBuilder().
            module( new HttpModule() ).
            build()

        when:
        def factory = engine.getInstance( HttpHandlerFactory.class );

        then:
        factory != null;
    }
}
