package io.purplejs.core.internal.util

import io.purplejs.core.exception.ProblemException
import spock.lang.Specification

class ErrorHelperTest
    extends Specification
{
    def "problemException"()
    {
        setup:
        def e = ProblemException.
            newBuilder().
            build();

        when:
        def result = ErrorHelper.INSTANCE.handleError( e );

        then:
        e == result;
    }

    def "scriptException"()
    {
        setup:
        def e = new javax.script.ScriptException( "Some problem", "/a/b/test.js", 1 );

        when:
        def result = ErrorHelper.INSTANCE.handleError( e );

        then:
        result != null;
        result instanceof ProblemException;

        when:
        def problem = (ProblemException) result;

        then:
        problem.message == "Some problem in /a/b/test.js at line number 1";
        problem.lineNumber == 1;
        problem.cause == e;
    }

    def "scriptException with cause"()
    {
        setup:
        def cause = new RuntimeException( "Other error" );
        def e = new javax.script.ScriptException( "Some problem", "/a/b/test.js", 1 );
        e.initCause( cause );

        when:
        def result = ErrorHelper.INSTANCE.handleError( e );

        then:
        result != null;
        result instanceof ProblemException;

        when:
        def problem = (ProblemException) result;

        then:
        problem.message == "Other error";
        problem.lineNumber == 1;
        problem.cause == cause;
    }

    def "runtimeException"()
    {
        setup:
        def e = new RuntimeException( "Some problem" );

        when:
        def result = ErrorHelper.INSTANCE.handleError( e );

        then:
        result == e;
    }

    def "other exception"()
    {
        setup:
        def e = new Exception( "Some problem" );

        when:
        def result = ErrorHelper.INSTANCE.handleError( e );

        then:
        result != null;
        result.cause == e;
    }
}
