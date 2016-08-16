package io.purplejs.testing.mock;

import org.junit.Test;

import com.google.common.base.Charsets;

import io.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class MockResourceTest
{
    @Test
    public void getters()
        throws Exception
    {
        final ResourcePath path = ResourcePath.from( "/a/b.js" );
        final MockResource resource = new MockResource( path, "hello".getBytes( Charsets.UTF_8 ) );

        assertEquals( path, resource.getPath() );
        assertEquals( 5, resource.getSize() );
        assertTrue( resource.getLastModified() > 0 );
        assertEquals( "hello", resource.getBytes().asCharSource( Charsets.UTF_8 ).read() );

        resource.setLastModified( 0 );
        assertEquals( 0, resource.getLastModified() );
    }
}
