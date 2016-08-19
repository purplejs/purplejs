package io.purplejs.core.internal.nashorn

import spock.lang.Specification

class NashornRuntimeTest
    extends Specification
{
    def NashornRuntime runtime;

    def setup()
    {
        def classLoader = getClass().getClassLoader();
        this.runtime = new NashornRuntimeFactory().newRuntime( classLoader );
    }

    def "getEngine"()
    {
        when:
        def engine = this.runtime.engine;

        then:
        engine != null;
    }

    def "newJsArray"()
    {
        when:
        def value = this.runtime.newJsArray();

        then:
        value != null;
        value.getClassName() == 'Array';
    }

    def "newJsObject"()
    {
        when:
        def value = this.runtime.newJsObject();

        then:
        value != null;
        value.getClassName() == 'Object';
    }

    def "toJsonString"()
    {
        when:
        def value = this.runtime.newJsArray();
        def json = this.runtime.toJsonString( value );

        then:
        json == '[]';

        when:
        value = this.runtime.newJsObject();
        json = this.runtime.toJsonString( value );

        then:
        json == '{}';
    }

    def "parseJson"()
    {
        when:
        def value = this.runtime.parseJson( '[1,2]' );

        then:
        this.runtime.toJsonString( value ) == '[1,2]';

        when:
        value = this.runtime.parseJson( '{"a":1}' );

        then:
        this.runtime.toJsonString( value ) == '{"a":1}';
    }
}
