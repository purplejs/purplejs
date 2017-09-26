package io.purplejs.core.internal.value

import io.purplejs.core.internal.nashorn.NashornRuntime
import io.purplejs.core.internal.nashorn.NashornRuntimeFactory
import io.purplejs.core.value.ScriptValue
import jdk.nashorn.api.scripting.ScriptObjectMirror
import spock.lang.Specification

import javax.script.ScriptEngine
import java.text.SimpleDateFormat
import java.util.function.Function

class ScriptValueFactoryImplTest
    extends Specification
{
    def ScriptValueFactory factory;

    def ScriptEngine engine;

    def setup()
    {
        final NashornRuntime runtime = new NashornRuntimeFactory().newRuntime( getClass().getClassLoader() );
        this.engine = runtime.getEngine();

        this.factory = new ScriptValueFactoryImpl( runtime );
    }

    def "newValue of null"()
    {
        when:
        def value = this.factory.newValue( null );

        then:
        value == null;
    }

    def "newValue from scalar"()
    {
        when:
        def value = this.factory.newValue( "2" );

        then:
        value != null;
        !value.isArray();
        !value.isFunction();
        !value.isObject();
        value.isValue();

        value.getValue() == "2";
        value.toJavaObject() == "2";
        value.getRaw() == "2";
        value.getValue( Integer.class ) == 2;

        assertNonArray( value );
        assertNonObject( value );
        assertNonFunction( value );
    }

    def "newValue from function"()
    {
        when:
        def result = execute( "var result = function(a, b) { return a + b; }; result;" );
        def value = this.factory.newValue( result );

        then:
        value != null;
        !value.isArray();
        value.isFunction();
        !value.isObject();
        !value.isValue();

        value.getRaw() != null;
        value.getRaw() instanceof ScriptObjectMirror;

        assertNonValue( value );
        assertNonArray( value );
        assertNonObject( value );

        when:
        result = value.call( 10, 11 );

        then:
        result != null;
        result.isValue();
        result.getValue() == 21.0;

        when:
        def func = (Function<Object[], Object>) value.toJavaObject();
        result = func.apply( [10, 11].toArray() );

        then:
        result != null;
        result == 21.0;
    }

    def "newValue from array"()
    {
        when:
        def result = execute( "var result = ['1', '2', undefined]; result;" );
        def value = this.factory.newValue( result );

        then:
        value != null;
        value.isArray();
        !value.isFunction();
        !value.isObject();
        !value.isValue();

        value.getRaw() != null;
        value.getRaw() instanceof ScriptObjectMirror;

        assertNonValue( value );
        assertNonObject( value );
        assertNonFunction( value );

        value.getArray() != null;
        value.getArray().size() == 2;
        value.getArray().get( 0 ).getValue() == "1";
        value.getArray().get( 1 ).getValue() == "2";

        value.toJavaObject() == ["1", "2"];
    }

    def "newValue from object"()
    {
        when:
        def result = execute( "var result = {'a':1, 'b':2}; result;" );
        def value = this.factory.newValue( result );

        then:
        value != null;
        !value.isArray();
        !value.isFunction();
        value.isObject();
        !value.isValue();

        value.getRaw() != null;
        value.getRaw() instanceof ScriptObjectMirror;

        assertNonValue( value );
        assertNonArray( value );
        assertNonFunction( value );

        value.getKeys() != null;
        value.getKeys().size() == 2;
        value.getKeys().toString() == '[a, b]';

        value.getMember( "a" ) != null;
        value.getMember( "a" ).getValue() == 1;
        value.hasMember( "a" );

        value.toJavaObject() == [a: 1, b: 2];
    }

    def "newValue from date"()
    {
        setup:
        def format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
        format.setTimeZone( TimeZone.getTimeZone( "UTC" ) );

        when:
        def result = execute( "var result = new Date(Date.parse('1995-11-12T22:24:25Z')); result;" );
        def value = this.factory.newValue( result );

        then:
        value != null;
        !value.isArray();
        !value.isFunction();
        !value.isObject();
        value.isValue();

        when:
        def date = (Date) value.getValue();

        then:
        "1995-11-12T22:24:25+0000" == format.format( date );
    }

    private Object execute( final String script )
    {
        return this.engine.eval( script );
    }

    private static void assertNonValue( final ScriptValue value )
    {
        assert value.getValue() == null;
        assert value.getValue( Integer.class ) == null;
    }

    private static void assertNonArray( final ScriptValue value )
    {
        assert value.getArray() != null;
        assert value.getArray().size() == 0;
    }

    private static void assertNonObject( final ScriptValue value )
    {
        assert value.getKeys() != null;
        assert value.getKeys().size() == 0;
        assert value.getMap().isEmpty()

        assert value.getMember( "test" ) == null;
        assert !value.hasMember( "test" );
    }

    private static void assertNonFunction( final ScriptValue value )
    {
        assert value.call( "a", "b" ) == null;
    }
}
