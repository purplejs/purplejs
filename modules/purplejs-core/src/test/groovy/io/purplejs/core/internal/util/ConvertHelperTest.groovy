package io.purplejs.core.internal.util

import spock.lang.Specification

class ConvertHelperTest
    extends Specification
{
    def "convert error"()
    {
        when:
        ConvertHelper.INSTANCE.convert( "abc", Integer.class );

        then:
        def IllegalArgumentException ex = thrown();
        ex.message == 'Failed to convert java.lang.String -> java.lang.Integer';
    }

    def "no such converter"()
    {
        when:
        ConvertHelper.INSTANCE.convert( "abc", Date.class );

        then:
        def IllegalArgumentException ex = thrown();
        ex.message == 'No such converter for java.lang.String -> java.util.Date';
    }

    def "convert null"()
    {
        when:
        def value = ConvertHelper.INSTANCE.convert( null, Boolean.class );

        then:
        value == null;
    }

    def "convert boolean"()
    {
        when:
        def value = ConvertHelper.INSTANCE.convert( from, Boolean.class );

        then:
        value != null;
        value == expected;

        where:
        from   | expected
        "true" | true
        true   | true
        1      | true
        0      | false
    }

    def "convert byte"()
    {
        when:
        def value = ConvertHelper.INSTANCE.convert( from, Byte.class );

        then:
        value != null;
        value == expected;

        where:
        from | expected
        "11" | new Byte( (byte) 11 )
        11   | new Byte( (byte) 11 )
    }

    def "convert double"()
    {
        when:
        def value = ConvertHelper.INSTANCE.convert( from, Double.class );

        then:
        value != null;
        value == expected;

        where:
        from | expected
        "11" | 11d
        11d  | 11d
    }

    def "convert float"()
    {
        when:
        def value = ConvertHelper.INSTANCE.convert( from, Float.class );

        then:
        value != null;
        value == expected;

        where:
        from | expected
        "11" | 11f
        11f  | 11f
    }

    def "convert integer"()
    {
        when:
        def value = ConvertHelper.INSTANCE.convert( from, Integer.class );

        then:
        value != null;
        value == expected;

        where:
        from | expected
        "11" | 11
        11   | 11
        11L  | 11
    }

    def "convert long"()
    {
        when:
        def value = ConvertHelper.INSTANCE.convert( from, Long.class );

        then:
        value != null;
        value == expected;

        where:
        from | expected
        "11" | 11L
        11   | 11L
    }

    def "convert short"()
    {
        when:
        def value = ConvertHelper.INSTANCE.convert( from, Short.class );

        then:
        value != null;
        value == expected;

        where:
        from | expected
        "11" | (short) 11
        11   | (short) 11
    }

    def "convert string"()
    {
        when:
        def value = ConvertHelper.INSTANCE.convert( from, String.class );

        then:
        value != null;
        value == expected;

        where:
        from | expected
        "11" | "11"
        11   | "11"
    }
}
