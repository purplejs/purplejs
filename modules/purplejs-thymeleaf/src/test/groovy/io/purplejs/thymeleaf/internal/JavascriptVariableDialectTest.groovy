package io.purplejs.thymeleaf.internal

import spock.lang.Specification

class JavascriptVariableDialectTest
    extends Specification
{
    def "getExpressionObjectFactory"()
    {
        setup:
        def dialect = new JavascriptVariableDialect();

        when:
        def result = dialect.getExpressionObjectFactory();

        then:
        result != null;

        when:
        result = result.getAllExpressionObjectNames();

        then:
        result.toString() == '[js]';
    }
}
