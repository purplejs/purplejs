package io.purplejs.testing

import io.purplejs.core.EngineBuilder
import spock.lang.Specification

class ScriptTestSupportTest
    extends Specification
{
    def "testFiles"()
    {
        setup:
        def support = new ScriptTestSupport() {};

        when:
        def files = support.getTestFiles();

        then:
        files.toList() == ['**/*-test.js'];

        when:
        support.setTestFiles( "a.js", "b.js" );
        files = support.getTestFiles();

        then:
        files.toList() == ['a.js', 'b.js'];
    }

    def "initialize"()
    {
        setup:
        def support = new ScriptTestSupport() {};
        def builder = EngineBuilder.newBuilder();

        when:
        support.initialize( builder );

        then: true;
    }
}
