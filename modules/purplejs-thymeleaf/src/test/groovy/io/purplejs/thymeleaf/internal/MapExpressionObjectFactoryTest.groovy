package io.purplejs.thymeleaf.internal

import spock.lang.Specification

class MapExpressionObjectFactoryTest
    extends Specification
{
    private MapExpressionObjectFactory factory;

    def setup()
    {
        this.factory = new MapExpressionObjectFactory();
    }

    def "getAllExpressionObjectNames"()
    {
        when:
        this.factory.put( "a", 1 );

        then:
        this.factory.getAllExpressionObjectNames().toString() == '[a]';
    }

    def "isCacheable"()
    {
        when:
        def flag = this.factory.isCacheable( 'a' );

        then:
        flag;
    }

    def "buildObject"()
    {
        when:
        this.factory.put( "a", 1 );
        def result = this.factory.buildObject( null, 'a' );

        then:
        result == 1;
    }
}
