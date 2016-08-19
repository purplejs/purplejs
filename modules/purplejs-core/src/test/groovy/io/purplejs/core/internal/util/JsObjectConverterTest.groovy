package io.purplejs.core.internal.util

import io.purplejs.core.internal.nashorn.NashornRuntime
import io.purplejs.core.internal.nashorn.NashornRuntimeFactory
import io.purplejs.core.json.JsonGenerator
import io.purplejs.core.json.JsonSerializable
import jdk.nashorn.api.scripting.ScriptObjectMirror
import spock.lang.Specification

class JsObjectConverterTest
    extends Specification
{
    def JsObjectConverter converter;

    def NashornRuntime runtime;

    def JsonSerializable simpleObject;

    def setup()
    {
        final ClassLoader classLoader = getClass().getClassLoader();
        this.runtime = new NashornRuntimeFactory().newRuntime( classLoader );
        this.converter = new JsObjectConverter( this.runtime );

        this.simpleObject = new JsonSerializable() {
            @Override
            void serialize( final JsonGenerator gen )
            {
                gen.map();
                gen.value( "a", 1 );
                gen.value( "b", 2 );
                gen.end();
            }
        };
    }

    def "toJs simple"()
    {
        when:
        def value = this.converter.toJs( 11 );

        then:
        value != null;
        value == 11;
    }

    def "toJs serializable"()
    {
        when:
        def value = this.converter.toJs( this.simpleObject );

        then:
        value != null;
        value instanceof ScriptObjectMirror;
        this.runtime.toJsonString( value ) == '{"a":1,"b":2}';
    }

    def "toJs list"()
    {
        when:
        def value = this.converter.toJs( [1] );

        then:
        value != null;
        value instanceof ScriptObjectMirror;
        this.runtime.toJsonString( value ) == '[1]';

        when:
        value = this.converter.toJs( [1, [:]] );

        then:
        value != null;
        value instanceof ScriptObjectMirror;
        this.runtime.toJsonString( value ) == '[1,{}]';

        when:
        value = this.converter.toJs( [1, this.simpleObject] );

        then:
        value != null;
        value instanceof ScriptObjectMirror;
        this.runtime.toJsonString( value ) == '[1,{"a":1,"b":2}]';

        when:
        value = this.converter.toJs( [1, []] );

        then:
        value != null;
        value instanceof ScriptObjectMirror;
        this.runtime.toJsonString( value ) == '[1,[]]';
    }

    def "toJs map"()
    {
        when:
        def value = this.converter.toJs( [a: 1] );

        then:
        value != null;
        value instanceof ScriptObjectMirror;
        this.runtime.toJsonString( value ) == '{"a":1}';

        when:
        value = this.converter.toJs( [a: 1, b: [:]] );

        then:
        value != null;
        value instanceof ScriptObjectMirror;
        this.runtime.toJsonString( value ) == '{"a":1,"b":{}}';

        when:
        value = this.converter.toJs( [a: 1, b: this.simpleObject] );

        then:
        value != null;
        value instanceof ScriptObjectMirror;
        this.runtime.toJsonString( value ) == '{"a":1,"b":{"a":1,"b":2}}';

        when:
        value = this.converter.toJs( [a: 1, b: []] );

        then:
        value != null;
        value instanceof ScriptObjectMirror;
        this.runtime.toJsonString( value ) == '{"a":1,"b":[]}';
    }
}
