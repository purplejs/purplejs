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
        value != null;
        !value.isPresent();

        when:
        this.attrs.set( "key", "value" );
        value = this.attrs.get( "key" );

        then:
        value != null;
        value.isPresent();
        value.get() == 'value';

        when:
        this.attrs.remove( "key" );
        value = this.attrs.get( "key" );

        then:
        value != null;
        !value.isPresent();
    }

    def "typed access"()
    {
        setup:
        def typedValue = new TypedValue();

        when:
        def value = this.attrs.get( TypedValue.class );

        then:
        value != null;
        !value.isPresent();

        when:
        this.attrs.set( TypedValue.class, typedValue );
        value = this.attrs.get( TypedValue.class );

        then:
        value != null;
        value.isPresent();
        value.get() == typedValue;

        when:
        this.attrs.remove( TypedValue.class );
        value = this.attrs.get( TypedValue.class );

        then:
        value != null;
        !value.isPresent();
    }

    def "asMap"()
    {
        setup:
        def typedValue = new TypedValue();
        this.attrs.set( "key", "value" );
        this.attrs.set( TypedValue.class, typedValue );

        when:
        def map = this.attrs.asMap();

        then:
        map != null;
        map.size() == 2;
        map.get( "key" ) == 'value';
        map.get( TypedValue.class.name ) == typedValue;
    }
}
