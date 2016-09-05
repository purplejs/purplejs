package io.purplejs.core.internal.util

import spock.lang.Specification

class RequirementCheckerTest
    extends Specification
{
    def "versions should work"()
    {
        expect:
        newChecker( prop ).doCheck()

        where:
        prop << ["1.8.0_92", "1.8.0_94-internal", "1.9.0"]
    }

    def "java 1.7 should not work"()
    {
        when:
        newChecker( "1.7.0" ).doCheck();

        then:
        thrown RuntimeException;
    }

    def "java 1.8.0_20 should not work"()
    {
        when:
        newChecker( "1.8.0_20" ).doCheck();

        then:
        thrown RuntimeException;
    }

    private static RequirementChecker newChecker( final String value )
    {
        final Properties props = new Properties();
        props.put( "java.version", value );
        return new RequirementChecker( props );
    }
}
