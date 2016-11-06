package io.purplejs.core.internal.nashorn

import jdk.nashorn.api.scripting.ScriptObjectMirror
import jdk.nashorn.internal.runtime.ScriptRuntime
import spock.lang.Specification

import javax.script.ScriptEngine
import java.text.SimpleDateFormat

class NashornHelperTest
    extends Specification
{
    def ScriptEngine engine;

    private ScriptEngine createEngine()
    {
        final ClassLoader classLoader = getClass().getClassLoader();
        final NashornRuntimeFactory runtimeFactory = new NashornRuntimeFactory();
        return runtimeFactory.newRuntime( classLoader ).getEngine();
    }

    def setup()
    {
        this.engine = createEngine();
    }

    def "new instance"()
    {
        when:
        def helper = new NashornHelper();

        then:
        helper != null;
    }

    def "newScriptEngine"()
    {
        when:
        def engine1 = createEngine();

        then:
        engine1 != null;

        when:
        def engine2 = createEngine();

        then:
        engine2 != null;
        engine1 != engine2;
    }

    def "isUndefined"()
    {
        expect:
        expected == value;

        where:
        expected | value
        true     | NashornHelper.isUndefined( null )
        false    | NashornHelper.isUndefined( 11 )
        true     | NashornHelper.isUndefined( ScriptRuntime.UNDEFINED )
    }

    def "isDateType"()
    {
        when:
        def result = execute( 'var result = {}; result;' );

        then:
        !NashornHelper.isDateType( result );

        when:
        result = execute( 'var result = new Date(); result;' );

        then:
        NashornHelper.isDateType( result );
    }

    def "isArrayType"()
    {
        when:
        def result = execute( 'var result = 11; result;' );

        then:
        !NashornHelper.isArrayType( result );

        when:
        result = execute( 'var result = {}; result;' );

        then:
        !NashornHelper.isArrayType( result );

        when:
        result = execute( 'var result = []; result;' );

        then:
        NashornHelper.isArrayType( result );
    }

    def "isObjectType"()
    {
        when:
        def result = execute( 'var result = 11; result;' );

        then:
        !NashornHelper.isObjectType( result );

        when:
        result = execute( 'var result = []; result;' );

        then:
        !NashornHelper.isObjectType( result );

        when:
        result = execute( 'var result = {}; result;' );

        then:
        NashornHelper.isObjectType( result );
    }

    def "toDate"()
    {
        setup:
        def format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
        format.setTimeZone( TimeZone.getTimeZone( "UTC" ) );

        when:
        def value = execute( 'var result = new Date(Date.parse(\'1995-11-12T22:24:25Z\')); result;' );
        def date = NashornHelper.toDate( value );

        then:
        format.format( date ) == "1995-11-12T22:24:25+0000";
    }

    def "addToArray"()
    {
        when:
        def array = (ScriptObjectMirror) execute( 'var result = []; result;' );

        then:
        array.size() == 0;

        when:
        NashornHelper.addToArray( array, 10 );

        then:
        array.size() == 1;
    }

    def "addToObject"()
    {
        when:
        def object = (ScriptObjectMirror) execute( 'var result = {}; result;' );

        then:
        object.keySet().size() == 0;

        when:
        NashornHelper.addToObject( object, 'a', 10 );

        then:
        object.keySet().size() == 1;
    }

    private Object execute( final String script )
    {
        return this.engine.eval( script );
    }
}
