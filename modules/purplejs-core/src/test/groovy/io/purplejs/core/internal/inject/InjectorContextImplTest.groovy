package io.purplejs.core.internal.inject

import io.purplejs.core.Engine
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class InjectorContextImplTest
    extends Specification
{
    def "test getEngine"()
    {
        setup:
        def engine = Mock( Engine.class )
        def resourcePath = ResourcePath.from( "/a/b/c" )
        def instance = Mock( List.class )

        when:
        def injector = new InjectorContextImpl( engine, resourcePath, instance );

        then:
        injector.getEngine() != null
        injector.getEngine() == engine
    }

    def "test getResource"()
    {
        setup:
        def engine = Mock( Engine.class )
        def resourcePath = ResourcePath.from( "/a/b/c" )
        def instance = Mock( List.class )

        when:
        def injector = new InjectorContextImpl( engine, resourcePath, instance );

        then:
        injector.getResource() != null
        injector.getResource() == resourcePath
    }

    def "test getInstance"()
    {
        setup:
        def engine = Mock( Engine.class )
        def resourcePath = ResourcePath.from( "/a/b/c" )
        def instance = Mock( List.class )

        when:
        def injector = new InjectorContextImpl( engine, resourcePath, instance );

        then:
        injector.getInstance() != null
        injector.getInstance() == instance
    }
}
