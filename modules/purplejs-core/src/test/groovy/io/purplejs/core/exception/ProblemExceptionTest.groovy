package io.purplejs.core.exception

import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class ProblemExceptionTest
    extends Specification
{
    def "test simple"()
    {
        setup:
        def path = ResourcePath.from( "/test.js" );

        when:
        def ex = ProblemException.newBuilder().
            path( path ).
            lineNumber( 10 ).
            build();

        then:
        ex != null;
        ex.lineNumber == 10;
        ex.path == path;
        ex.callStack != null;
        ex.callStack.isEmpty();
        ex == ex.innerError;
        ex.message == "Empty message in exception";
    }

    def "test message"()
    {
        when:
        def ex = ProblemException.newBuilder().
            message( "A %s here" ).
            build();

        then:
        ex != null;
        ex.message == "A %s here";
    }

    def "test message with args"()
    {
        when:
        def ex = ProblemException.newBuilder().
            message( "A %s here", "problem" ).
            build();

        then:
        ex != null;
        ex.message == "A problem here";
    }

    def "test call stack"()
    {
        when:
        def ex = ProblemException.newBuilder().
            callLine( "first", 1 ).
            callLine( "second", 2 ).
            build();

        then:
        ex != null;
        ex.callStack != null;
        ex.callStack.size() == 2;
        ex.callStack.toString() == "[first at line 1, second at line 2]";
    }

    def "test inner error"()
    {
        setup:
        def cause1 = ProblemException.newBuilder().
            build();
        def cause2 = new Throwable( cause1 );

        def ex = ProblemException.newBuilder().
            cause( cause2 ).
            build();

        when:
        def inner = ex.innerError;

        then:
        inner == cause1;
    }
}
