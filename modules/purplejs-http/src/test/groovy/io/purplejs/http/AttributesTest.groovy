package io.purplejs.http

import spock.lang.Specification

class AttributesTest
    extends Specification
{
    def Attributes attrs;

    class TypedValue
    {}

    def setup()
    {
        this.attrs = new Attributes();
    }

    def "untyped access"()
    {
        when:
        def value = this.attrs.get( "key" );

        then:
        value == null;

        when:
        this.attrs.put( "key", "value" );
        value = this.attrs.get( "key" );

        then:
        value == 'value';

        when:
        this.attrs.remove( "key" );
        value = this.attrs.get( "key" );

        then:
        value == null;
    }

    def "typed access"()
    {
        setup:
        def typedValue = new TypedValue();

        when:
        def value = this.attrs.get( TypedValue.class );

        then:
        value == null;

        when:
        this.attrs.put( TypedValue.class, typedValue );
        value = this.attrs.get( TypedValue.class );

        then:
        value == typedValue;

        when:
        this.attrs.remove( TypedValue.class );
        value = this.attrs.get( TypedValue.class );

        then:
        value == null;
    }
}
