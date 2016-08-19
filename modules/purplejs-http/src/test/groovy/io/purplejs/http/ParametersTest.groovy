package io.purplejs.http

import spock.lang.Specification

class ParametersTest
    extends Specification
{
    def Parameters params;

    def setup()
    {
        this.params = new Parameters();
    }

    def "get"()
    {
        when:
        def values = this.params.get( "key" );

        then:
        values != null;
        values.isEmpty();

        when:
        this.params.put( "key", "value1" );
        this.params.put( "key", "value2" );
        values = this.params.get( "key" );

        then:
        values != null;
        values.size() == 2;
        values.toString() == '[value2, value1]';

        when:
        this.params.remove( "key" );
        values = this.params.get( "key" );

        then:
        values != null;
        values.isEmpty();
    }

    def "getFirst"()
    {
        when:
        def value = this.params.getFirst( "key" );

        then:
        value != null;
        !value.isPresent();

        when:
        this.params.put( "key", "value1" );
        this.params.put( "key", "value2" );
        value = this.params.getFirst( "key" );

        then:
        value != null;
        value.isPresent();
        value.get() == 'value2';

        when:
        this.params.remove( "key" );
        value = this.params.getFirst( "key" );

        then:
        value != null;
        !value.isPresent();
    }

    def "asMap"()
    {
        when:
        this.params.put( "key", "value" );
        def map = this.params.asMap();

        then:
        map != null;
        map.size() == 1;
        map.toString() == '{key=[value]}';
    }
}
