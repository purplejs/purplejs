package io.purplejs.core

import io.purplejs.core.inject.BeanInjector
import io.purplejs.core.resource.ResourceLoader
import io.purplejs.core.resource.ResourceResolver
import io.purplejs.core.settings.SettingsBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.util.function.Consumer

class EngineTest
    extends Specification
{
    @Rule
    TemporaryFolder temporaryFolder = new TemporaryFolder();

    def "getSettings"()
    {
        setup:
        def settings = SettingsBuilder.newBuilder().build();

        when:
        def engine = EngineBuilder.newBuilder().
            settings( settings ).
            build();

        then:
        engine != null;
        engine.settings == settings;
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
        engine.getDevSourceDirs() == [this.temporaryFolder.root];

        cleanup:
        RunMode.PROD.set();
    }

    def "getClassLoader"()
    {
        setup:
        def classLoader = new URLClassLoader();

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

    def "getInstanceOrNull"()
    {
        when:
        def engine = EngineBuilder.newBuilder().
            module { binder -> binder.instance( String.class, "hello" ) }.
            build();

        then:
        engine != null;
        engine.getInstanceOrNull( String.class ) == "hello";
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

    def "getResourceResolver"()
    {
        setup:
        def resolver = Mock( ResourceResolver.class )

        when:
        def engine = EngineBuilder.newBuilder().
            resourceResolver( resolver ).
            build()

        then:
        engine != null
        engine.resourceResolver == resolver
    }

    def "getBeanInjector"()
    {
        setup:
        def injector = Mock( BeanInjector.class )

        when:
        def engine = EngineBuilder.newBuilder().
            beanInjector( injector ).
            build()

        then:
        engine != null
        engine.beanInjector == injector
    }
}
