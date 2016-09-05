package io.purplejs.core.internal.util

import spock.lang.Specification

class JavaVersionTest
    extends Specification
{
    def "test version"()
    {
        setup:
        def version = newVersion( prop );

        expect:
        version.isJava8() == is8;
        version.isJava9() == is9;
        version.getUpdate() == update;

        where:
        prop                | is8   | is9   | update
        "1.7.0"             | false | false | 0
        "1.8.0_40"          | true  | false | 40
        "1.8.0_45-internal" | true  | false | 45
        "1.9.0_11"          | false | true  | 11
    }

    private static JavaVersion newVersion( final String value )
    {
        final Properties props = new Properties();
        props.put( "java.version", value );
        return new JavaVersion( props );
    }
}
