package io.purplejs.core.mock;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourcePath;

import static org.junit.Assert.*;

public class MockResourceLoaderTest
{
    private MockResourceLoader loader;

    @Before
    public void setUp()
    {
        this.loader = new MockResourceLoader();
    }

    @Test
    public void load_notFound()
    {
        assertNull( this.loader.loadOrNull( ResourcePath.from( "/a/b.js" ) ) );
    }

    @Test
    public void addResource_text()
        throws Exception
    {
        assertNotNull( this.loader.addResource( "/a/hello1.js", "hello1" ) );
        assertNotNull( this.loader.addResource( ResourcePath.from( "/a/hello2.js" ), "hello2" ) );

        assertResource( "/a/hello1.js", "hello1" );
        assertResource( "/a/hello2.js", "hello2" );
    }

    @Test
    public void addResource_bytes()
        throws Exception
    {
        assertNotNull( this.loader.addResource( "/a/hello1.js", "hello1".getBytes( Charsets.UTF_8 ) ) );
        assertNotNull( this.loader.addResource( ResourcePath.from( "/a/hello2.js" ), "hello2".getBytes( Charsets.UTF_8 ) ) );

        assertResource( "/a/hello1.js", "hello1" );
        assertResource( "/a/hello2.js", "hello2" );
    }

    private void assertResource( final String path, final String text )
        throws Exception
    {
        final ResourcePath resourcePath = ResourcePath.from( path );
        final Resource resource = this.loader.loadOrNull( resourcePath );
        assertNotNull( resource );
        assertEquals( resourcePath, resource.getPath() );
        assertEquals( text, resource.getBytes().asCharSource( Charsets.UTF_8 ).read() );
    }
}
