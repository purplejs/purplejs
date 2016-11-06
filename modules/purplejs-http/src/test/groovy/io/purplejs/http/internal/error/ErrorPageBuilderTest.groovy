package io.purplejs.http.internal.error

import spock.lang.Specification

class ErrorPageBuilderTest
    extends Specification
{
    def "findTrace"()
    {
        setup:
        def cause = newThrowable( 10 );

        when:
        def list = ErrorPageBuilder.findTrace( cause );

        then:
        list.size() == 10;
    }

    def "findTrace (too many)"()
    {
        setup:
        def cause = newThrowable( 30 );

        when:
        def list = ErrorPageBuilder.findTrace( cause );

        then:
        list.size() == 21;
        list.get(20).text == '...';
    }

    private static Throwable newThrowable( final int numLines )
    {
        final Throwable cause = new Throwable();
        final StackTraceElement[] elements = new StackTraceElement[numLines];

        for ( int i = 0; i < numLines; i++ )
        {
            elements[i] = new StackTraceElement( 'test', 'test', 'test', 1 );
        }

        cause.setStackTrace( elements );
        return cause;
    }
}
