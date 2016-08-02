package io.purplejs.resource;

import org.junit.Test;

import com.google.common.base.Charsets;

import static org.junit.Assert.*;

public class BytesResourceTest
{
    @Test
    public void fromText()
        throws Exception
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        final BytesResource resource = BytesResource.create( path, "hello" );

        assertEquals( path, resource.getPath() );
        assertEquals( 5, resource.getSize() );
        assertTrue( resource.getLastModified() > 0 );
        assertNotNull( resource.getBytes() );
        assertEquals( "hello", resource.getBytes().asCharSource( Charsets.UTF_8 ).read() );
    }

    @Test
    public void fromBytes()
        throws Exception
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        final BytesResource resource = BytesResource.create( path, "hello".getBytes( Charsets.UTF_8 ) );

        assertEquals( path, resource.getPath() );
        assertEquals( 5, resource.getSize() );
        assertTrue( resource.getLastModified() > 0 );
        assertNotNull( resource.getBytes() );
        assertEquals( "hello", resource.getBytes().asCharSource( Charsets.UTF_8 ).read() );
    }
}
