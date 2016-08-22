package io.purplejs.http.internal.error

import spock.lang.Specification

class HtmlBuilderTest
    extends Specification
{
    def "build html"()
    {
        setup:
        def builder = new HtmlBuilder().
            open( 'tag' ).
            attribute( 'a', '1' ).
            escapedText( 'hello' ).
            text( '-world' ).
            close().
            open( 'other' ).
            closeEmpty();

        when:
        def str = builder.toString();

        then:
        str == '<tag a="1">hello-world</tag><other/>';
    }
}
