package io.purplejs.core.internal.util

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
}
