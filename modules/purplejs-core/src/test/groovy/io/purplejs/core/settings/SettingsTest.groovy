package io.purplejs.core.settings

import spock.lang.Specification

class SettingsTest
    extends Specification
{
    def "test get"()
    {
        setup:
        def settings = SettingsBuilder.newBuilder().
            put( 'known', true ).
            build();

        when:
        def value = settings.get( 'unknown' );

        then:
        !value.isPresent();

        when:
        value = settings.get( 'known' );

        then:
        value.isPresent();
        value.get() == 'true';
    }

    def "test get, built-in converter"()
    {
        setup:
        def settings = SettingsBuilder.newBuilder().
            put( 'known', true ).
            build();

        when:
        def value = settings.get( Integer.class, 'unknown' );

        then:
        !value.isPresent();

        when:
        value = settings.get( Integer.class, 'known' );

        then:
        !value.isPresent();

        when:
        value = settings.get( Boolean.class, 'known' );

        then:
        value.isPresent();
        value.get();
    }

    def "test get, converter"()
    {
        setup:
        def settings = SettingsBuilder.newBuilder().
            put( 'known', true ).
            build();

        when:
        def value = settings.get( Integer.class, 'unknown', { str -> Integer.parseInt( str ) } );

        then:
        !value.isPresent();

        when:
        value = settings.get( Boolean.class, 'known', { str -> Boolean.parseBoolean( str ) } );

        then:
        value.isPresent();
        value.get();
    }

    def "test getAsArray"()
    {
        setup:
        def settings = SettingsBuilder.newBuilder().
            put( 'known', "1,2,3" ).
            build();

        when:
        def value = settings.getAsArray( 'unknown' );

        then:
        value.isEmpty();

        when:
        value = settings.getAsArray( 'known' );

        then:
        value.toString() == '[1, 2, 3]';
    }

    def "test getAsArray, built-in converter"()
    {
        setup:
        def settings = SettingsBuilder.newBuilder().
            put( 'known', "true,false" ).
            build();

        when:
        def value = settings.getAsArray( Boolean.class, 'unknown' );

        then:
        value.isEmpty();

        when:
        value = settings.getAsArray( Integer.class, 'known' );

        then:
        value.isEmpty();

        when:
        value = settings.getAsArray( Boolean.class, 'known' );

        then:
        value.toString() == '[true, false]';
    }

    def "test getAsArray, converter"()
    {
        setup:
        def settings = SettingsBuilder.newBuilder().
            put( 'known', "1,2,3" ).
            build();

        when:
        def value = settings.getAsArray( Integer.class, 'unknown', { str -> Integer.parseInt( str ) } );

        then:
        value.isEmpty();

        when:
        value = settings.getAsArray( Integer.class, 'known', { str -> Integer.parseInt( str ) } );

        then:
        value.toString() == '[1, 2, 3]';
    }

    def "test getAsSettings"()
    {
        setup:
        def settings = SettingsBuilder.newBuilder().
            put( 'a.b', '1' ).
            put( 'b', '2' ).
            put( 'a.b.c', '3' ).
            build();

        when:
        def value = settings.getAsSettings( 'a' );

        then:
        value.toString() == '{b=1, b.c=3}';
    }

    def "test asMap"()
    {
        setup:
        def settings = SettingsBuilder.newBuilder().
            put( 'a', '1' ).
            put( 'b', '2' ).
            build();

        when:
        def value = settings.asMap();

        then:
        value.toString() == '{a=1, b=2}';
    }
}
