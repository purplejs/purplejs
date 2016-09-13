package io.purplejs.core.util

import com.google.common.base.Charsets
import com.google.common.io.ByteSource
import com.google.common.io.CharSource
import spock.lang.Specification

class IOHelperTest
    extends Specification
{
    def "new instance"()
    {
        when:
        def instance = new IOHelper();

        then:
        instance != null;
    }

    def "toCharSource"()
    {
        setup:
        def bytes = ByteSource.wrap( 'hello'.bytes );

        when:
        def chars = IOHelper.toCharSource( bytes );

        then:
        chars != null;
        chars.read() == 'hello';
    }

    def "readString from bytes"()
    {
        setup:
        def bytes = ByteSource.wrap( 'hello'.bytes );

        when:
        def str = IOHelper.readString( bytes );

        then:
        str != null;
        str == 'hello';
    }

    def "readString from chars"()
    {
        setup:
        def chars = CharSource.wrap( 'hello' );

        when:
        def str = IOHelper.readString( chars );

        then:
        str != null;
        str == 'hello';
    }

    def "toByteSource from stream"()
    {
        setup:
        def bytes = ByteSource.wrap( 'hello'.bytes );

        when:
        def result = IOHelper.toByteSource( bytes.openStream() );

        then:
        result != null;
        result.asCharSource( Charsets.UTF_8 ).read() == 'hello';
    }

    def "readLines from chars"()
    {
        setup:
        def chars = CharSource.wrap( '1\n2\n' );

        when:
        def lines = IOHelper.readLines( chars );

        then:
        lines != null;
        lines == ['1', '2'];
    }

    def "readLines from stream"()
    {
        setup:
        def bytes = ByteSource.wrap( '1\n2\n'.bytes );

        when:
        def lines = IOHelper.readLines( bytes );

        then:
        lines != null;
        lines == ['1', '2'];
    }
}
