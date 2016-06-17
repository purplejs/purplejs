package io.purplejs.resource;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceNotFoundExceptionTest
{
    @Test
    public void testException()
    {
        final ResourcePath path = ResourcePath.from( "a.txt" );
        final ResourceNotFoundException ex = new ResourceNotFoundException( path );

        assertSame( path, ex.getResource() );
        Assert.assertEquals( "Resource [/a.txt] not found", ex.getMessage() );
    }
}
