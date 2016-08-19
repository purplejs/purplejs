package io.purplejs.core.internal.value

import io.purplejs.core.resource.ResourcePath
import io.purplejs.core.value.ScriptExports
import io.purplejs.core.value.ScriptValue
import spock.lang.Specification

class ScriptExportsImplTest
    extends Specification
{
    def ResourcePath resource;

    def ScriptValue value;

    def ScriptExports exports;

    def ScriptValue callResult;

    def setup()
    {
        this.resource = ResourcePath.from( "/a/b" );
        this.value = Mock( ScriptValue.class );

        def ScriptValue func = Mock( ScriptValue.class );
        this.callResult = Mock( ScriptValue.class );

        func.isFunction() >> true;
        func.call() >> this.callResult;
        this.value.getMember( "exists" ) >> func;
        this.value.getMember( "number" ) >> new ScriptValueFactoryImpl( null ).newValue( 1 );

        this.exports = new ScriptExportsImpl( this.resource, this.value );
    }

    def "getResource"()
    {
        when:
        def actual = this.exports.getResource();

        then:
        this.resource == actual;
    }

    def "getValue"()
    {
        when:
        def actual = this.exports.getValue();

        then:
        this.value == actual;
    }

    def "hasMethod"()
    {
        when:
        def result = this.exports.hasMethod( 'notExists' );

        then:
        !result;

        when:
        result = this.exports.hasMethod( 'exists' );

        then:
        result;
    }

    def "executeMethod"()
    {
        when:
        def result = this.exports.executeMethod( 'notExists' );

        then:
        result == null;

        when:
        result = this.exports.executeMethod( 'exists' );

        then:
        this.callResult == result;

        when:
        result = this.exports.executeMethod( 'number' );

        then:
        result == null;
    }
}
