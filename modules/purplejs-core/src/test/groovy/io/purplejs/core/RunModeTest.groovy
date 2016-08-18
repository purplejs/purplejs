package io.purplejs.core

import spock.lang.Specification
import spock.lang.Unroll

class RunModeTest
    extends Specification
{
    def "values"()
    {
        when:
        def values = RunMode.values();

        then:
        values.length == 3;
    }

    @Unroll
    def "valueOf #value"()
    {
        expect:
        expected == RunMode.valueOf( value );

        where:
        expected     | value
        RunMode.DEV  | 'DEV'
        RunMode.PROD | 'PROD'
        RunMode.TEST | 'TEST'
    }

    def "getCurrent"()
    {
        setup:
        RunMode.PROD.set();

        when:
        def mode = RunMode.get();

        then:
        mode == RunMode.PROD;
    }

    @Unroll
    def "set from property (#value)"()
    {
        setup:
        System.setProperty( 'io.purplejs.runMode', value );
        RunMode.init();

        expect:
        RunMode.get() == mode;

        where:
        value     | mode
        'unknown' | RunMode.PROD
        'dev'     | RunMode.DEV
        'test'    | RunMode.TEST
    }
}
