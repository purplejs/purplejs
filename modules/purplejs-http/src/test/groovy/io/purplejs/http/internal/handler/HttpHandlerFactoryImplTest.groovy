package io.purplejs.http.internal.handler

import io.purplejs.core.Engine
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class HttpHandlerFactoryImplTest
    extends Specification
{
    def "newHandler"()
    {
        setup:
        def engine = Mock( Engine.class );
        def factory = new HttpHandlerFactoryImpl();
        factory.init( engine );

        when:
        def path = ResourcePath.from( '/test.js' );
        def handler = factory.newHandler( path );

        then:
        handler != null;
        handler instanceof HttpHandlerImpl;

        when:
        def handlerImpl = (HttpHandlerImpl) handler;

        then:
        handlerImpl.engine == engine;
        handlerImpl.errorRenderer != null;
        handlerImpl.resource == path;
    }
}
