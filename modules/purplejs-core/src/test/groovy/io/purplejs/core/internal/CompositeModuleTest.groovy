package io.purplejs.core.internal

import io.purplejs.core.Engine
import io.purplejs.core.EngineBinder
import io.purplejs.core.EngineModule
import spock.lang.Specification

import java.util.function.Consumer

class CompositeModuleTest
    extends Specification
{
    def CompositeModule compositeModule;

    def setup()
    {
        this.compositeModule = new CompositeModule();
    }

    def "configure"()
    {
        setup:
        def binder = Mock( EngineBinder.class );
        def module1 = Mock( EngineModule.class );
        def module2 = Mock( EngineModule.class );

        this.compositeModule.add( module1 );
        this.compositeModule.add( module2 );

        when:
        this.compositeModule.configure( binder );

        then:
        1 * module1.configure( binder );
        1 * module2.configure( binder );
    }

    def "init"()
    {
        setup:
        def engine = Mock( Engine.class );
        def initializer1 = Mock( Consumer.class );
        def initializer2 = Mock( Consumer.class );

        this.compositeModule.addInitializer( initializer1 );
        this.compositeModule.addInitializer( initializer2 );

        when:
        this.compositeModule.init( engine );

        then:
        1 * initializer1.accept( engine );
        1 * initializer2.accept( engine );
    }

    def "dispose"()
    {
        setup:
        def engine = Mock( Engine.class );
        def disposer1 = Mock( Consumer.class );
        def disposer2 = Mock( Consumer.class );

        this.compositeModule.addDisposer( disposer1 );
        this.compositeModule.addDisposer( disposer2 );

        when:
        this.compositeModule.dispose( engine );

        then:
        1 * disposer1.accept( engine );
        1 * disposer2.accept( engine );
    }
}
