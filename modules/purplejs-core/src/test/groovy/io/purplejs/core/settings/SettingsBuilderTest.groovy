package io.purplejs.core.settings

import spock.lang.Specification

class SettingsBuilderTest
    extends Specification
{
    def "put values"()
    {
        when:
        def settings = SettingsBuilder.newBuilder().
            put( 'a', 'value' ).
            put( 'b', true ).
            put( 'c', 10 ).
            build();

        then:
        settings.toString() == '{a=value, b=true, c=10}';
    }

    def "put array"()
    {
        when:
        def settings = SettingsBuilder.newBuilder().
            putArray( 'a', '1', '2', '3' ).
            build();

        then:
        settings.toString() == '{a=1,2,3}';
    }

    def "put map"()
    {
        setup:
        def map = ['a': '1', 'b': '2'];

        when:
        def settings = SettingsBuilder.newBuilder().
            put( map ).
            put( 'c', map ).
            build();

        then:
        settings.toString() == '{a=1, b=2, c.a=1, c.b=2}';
    }

    def "put properties"()
    {
        setup:
        def props = new Properties();
        props.put( 'a', '1' );
        props.put( 'b', '2' );

        when:
        def settings = SettingsBuilder.newBuilder().
            put( props ).
            put( 'c', props ).
            build();

        then:
        settings.toString() == '{a=1, b=2, c.a=1, c.b=2}';
    }

    def "put settings"()
    {
        setup:
        def other = SettingsBuilder.newBuilder().
            put( 'a', '1' ).
            put( 'b', '2' ).
            build();

        when:
        def settings = SettingsBuilder.newBuilder().
            put( other ).
            put( 'c', other ).
            build();

        then:
        settings.toString() == '{a=1, b=2, c.a=1, c.b=2}';
    }
}
