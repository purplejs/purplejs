package io.purplejs.core.exception;

import org.junit.Test;

import io.purplejs.core.resource.ResourcePath;

import static org.junit.Assert.*;

public class ProblemExceptionTest
{
    @Test
    public void testSimple()
    {
        final ResourcePath path = ResourcePath.from( "/test.js" );

        final ProblemException.Builder builder = ProblemException.newBuilder();
        builder.path( path );
        builder.lineNumber( 10 );

        final ProblemException ex = builder.build();
        assertNotNull( ex );
        assertEquals( 10, ex.getLineNumber() );
        assertEquals( path, ex.getPath() );
        assertNotNull( ex.getCallStack() );
        assertTrue( ex.getCallStack().isEmpty() );
        assertSame( ex, ex.getInnerError() );
        assertEquals( "Empty message in exception", ex.getMessage() );
    }

    @Test
    public void testMessage()
    {
        final ProblemException.Builder builder = ProblemException.newBuilder();
        builder.message( "A %s here" );

        final ProblemException ex = builder.build();
        assertNotNull( ex );
        assertEquals( "A %s here", ex.getMessage() );
    }

    @Test
    public void testMessageWithArgs()
    {
        final ProblemException.Builder builder = ProblemException.newBuilder();
        builder.message( "A %s here", "problem" );

        final ProblemException ex = builder.build();
        assertNotNull( ex );
        assertEquals( "A problem here", ex.getMessage() );
    }

    @Test
    public void testCallStack()
    {
        final ProblemException.Builder builder = ProblemException.newBuilder();
        builder.callLine( "first", 1 );
        builder.callLine( "second", 2 );

        final ProblemException ex = builder.build();
        assertNotNull( ex );
        assertNotNull( ex.getCallStack() );
        assertEquals( 2, ex.getCallStack().size() );
        assertEquals( "[first at line 1, second at line 2]", ex.getCallStack().toString() );
    }

    @Test
    public void testInnerError()
    {
        final ProblemException.Builder builder1 = ProblemException.newBuilder();
        ProblemException cause1 = builder1.build();

        final Throwable cause2 = new Throwable( cause1 );

        final ProblemException.Builder builder2 = ProblemException.newBuilder();
        builder2.cause( cause2 );

        final ProblemException ex = builder2.build();
        assertNotNull( ex );

        final ProblemException inner = ex.getInnerError();
        assertSame( cause1, inner );
    }
}
