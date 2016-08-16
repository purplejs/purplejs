package io.purplejs.core.internal.util;

import javax.script.ScriptException;

import org.junit.Test;

import io.purplejs.core.exception.ProblemException;

import static org.junit.Assert.*;

public class ErrorHelperTest
{
    @Test
    public void handleError_resourceProblemException()
    {
        final ProblemException e = ProblemException.newBuilder().
            build();

        final RuntimeException result = ErrorHelper.INSTANCE.handleError( e );
        assertSame( e, result );
    }

    @Test
    public void handleError_scriptException()
    {
        final ScriptException e = new ScriptException( "Some problem", "/a/b/test.js", 1 );
        final RuntimeException result = ErrorHelper.INSTANCE.handleError( e );

        assertNotNull( result );
        assertTrue( result instanceof ProblemException );

        final ProblemException problem = (ProblemException) result;
        assertEquals( "Some problem in /a/b/test.js at line number 1", problem.getMessage() );
        assertEquals( 1, problem.getLineNumber() );
        assertSame( e, problem.getCause() );
    }

    @Test
    public void handleError_scriptException_withCause()
    {
        final RuntimeException cause = new RuntimeException( "Other error" );
        final ScriptException e = new ScriptException( "Some problem", "/a/b/test.js", 1 );
        e.initCause( cause );

        final RuntimeException result = ErrorHelper.INSTANCE.handleError( e );

        assertNotNull( result );
        assertTrue( result instanceof ProblemException );

        final ProblemException problem = (ProblemException) result;
        assertEquals( "Other error", problem.getMessage() );
        assertEquals( 1, problem.getLineNumber() );
        assertSame( cause, problem.getCause() );
    }

    @Test
    public void handleError_runtimeException()
    {
        final RuntimeException e = new RuntimeException( "Some problem" );
        final RuntimeException result = ErrorHelper.INSTANCE.handleError( e );

        assertSame( e, result );
    }

    @Test
    public void handleError_other()
    {
        final Exception e = new Exception( "Some problem" );
        final RuntimeException result = ErrorHelper.INSTANCE.handleError( e );

        assertNotNull( result );
        assertSame( e, result.getCause() );
    }
}
