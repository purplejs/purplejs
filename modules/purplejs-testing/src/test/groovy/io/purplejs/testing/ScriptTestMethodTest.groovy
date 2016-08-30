package io.purplejs.testing

import spock.lang.Specification

import java.util.function.Consumer

class ScriptTestMethodTest
    extends Specification
{
    def ScriptTestMethod method;

    def Consumer<Object[]> consumer;

    def setup()
    {
        this.consumer = Mock( Consumer.class );
        this.method = new ScriptTestMethod( 'test something', this.consumer );
    }

    def "getName"()
    {
        when:
        def name = this.method.getName();

        then:
        name == 'test something';
    }

    def "runTest"()
    {
        when:
        this.method.runTest( 'a', 'b' );

        then:
        1 * this.consumer.accept( ['a', 'b'] );
    }
}
