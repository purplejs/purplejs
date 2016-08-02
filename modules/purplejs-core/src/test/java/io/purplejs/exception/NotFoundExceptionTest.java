package io.purplejs.exception;

import org.junit.Test;

import io.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class NotFoundExceptionTest
{
    @Test
    public void withPath()
    {
        final ResourcePath path = ResourcePath.from( "a.txt" );
        final NotFoundException ex = new NotFoundException( path );

        assertEquals( "Resource [/a.txt] not found", ex.getMessage() );
    }

    @Test
    public void withMessage()
    {
        final NotFoundException ex = new NotFoundException( "message" );
        assertEquals( "message", ex.getMessage() );
    }
}
