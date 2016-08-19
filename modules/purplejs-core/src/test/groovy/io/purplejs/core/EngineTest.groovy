package io.purplejs.core

import io.purplejs.core.resource.ResourceLoader
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.util.function.Consumer

class EngineTest
    extends Specification
{
    @Rule
    def TemporaryFolder temporaryFolder = new TemporaryFolder();

    def "getConfig"()
    {
        when:
        def engine = EngineBuilder.newBuilder().
            module { binder -> binder.config( "key", "value" ) }.
            build();

        then:
        engine != null;
        engine.config != null;
        engine.config.size() == 1;
    }

    def "devSourceDir"()
    {
        setup:
        RunMode.DEV.set();

        when:
        def engine = EngineBuilder.newBuilder().
            devSourceDir( this.temporaryFolder.root ).
            build();

        then:
        engine != null;

        cleanup:
        RunMode.PROD.set();
    }

    def "getClassLoader"()
    {
        setup:
        def classLoader = Mock( ClassLoader.class );

        when:
        def engine = EngineBuilder.newBuilder().
            build();

        then:
        engine != null;
        engine.classLoader == getClass().getClassLoader();

        when:
        engine = EngineBuilder.newBuilder().
            classLoader( classLoader ).
            build();

        then:
        engine != null;
        engine.classLoader == classLoader;
    }

    def "getInstance"()
    {
        when:
        def engine = EngineBuilder.newBuilder().
            module { binder -> binder.instance( String.class, "hello" ) }.
            build();

        then:
        engine != null;
        engine.getInstance( String.class ) == "hello";
    }

    def "getProvider"()
    {
        when:
        def engine = EngineBuilder.newBuilder().
            module { binder -> binder.provider( String.class, { "hello" } ) }.
            build();

        then:
        engine != null;
        engine.getProvider( String.class ).get() == "hello";
    }

    def "getOptional"()
    {
        when:
        def engine = EngineBuilder.newBuilder().
            module { binder -> binder.instance( String.class, "hello" ) }.
            build();

        then:
        engine != null;
        engine.getOptional( String.class ).orElse( null ) == "hello";
    }

    def "initializer"()
    {
        setup:
        def initializer = Mock( Consumer.class );

        when:
        def engine = EngineBuilder.newBuilder().
            module { binder -> binder.initializer( initializer ) }.
            build();

        then:
        engine != null;
        1 * initializer.accept( _ );
    }

    def "disposer"()
    {
        setup:
        def disposer = Mock( Consumer.class );

        when:
        def engine = EngineBuilder.newBuilder().
            module { binder -> binder.disposer( disposer ) }.
            build();

        then:
        engine != null;
        0 * disposer.accept( _ );

        when:
        engine.dispose();

        then:
        1 * disposer.accept( _ );
    }

    def "getResourceLoader"()
    {
        setup:
        def loader = Mock( ResourceLoader.class );

        when:
        def engine = EngineBuilder.newBuilder().
            resourceLoader( loader ).
            build();

        then:
        engine != null;
        engine.resourceLoader == loader;
    }
}
