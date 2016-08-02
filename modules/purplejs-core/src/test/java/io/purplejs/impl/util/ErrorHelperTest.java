package io.purplejs.impl.util;

import javax.script.ScriptException;

import org.junit.Test;

import io.purplejs.resource.ResourceProblemException;

import static org.junit.Assert.*;

public class ErrorHelperTest
{
    @Test
    public void handleError_resourceProblemException()
    {
        final ResourceProblemException e = ResourceProblemException.newBuilder().
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
        assertTrue( result instanceof ResourceProblemException );

        final ResourceProblemException problem = (ResourceProblemException) result;
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
        assertTrue( result instanceof ResourceProblemException );

        final ResourceProblemException problem = (ResourceProblemException) result;
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
