package io.purplejs.core.resource;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.base.Charsets;

import static org.junit.Assert.*;

public class UrlResourceTest
{
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File file;

    @Before
    public void setUp()
        throws Exception
    {
        final ResourceTestFixture fixture = new ResourceTestFixture( this.temporaryFolder );
        this.file = fixture.createFile( "a.txt", "hello" );
    }

    @Test
    public void accessors()
        throws Exception
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        final UrlResource resource = new UrlResource( path, this.file.toURI().toURL() );

        assertEquals( path, resource.getPath() );
        assertEquals( this.file.length(), resource.getSize() );
        assertEquals( this.file.lastModified(), resource.getLastModified() );
        assertNotNull( resource.getBytes() );
        assertEquals( "hello", resource.getBytes().asCharSource( Charsets.UTF_8 ).read() );
    }

    @Test
    public void url_problem()
        throws Exception
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        final UrlResource resource = new UrlResource( path, new URL( "file:not-found" ) );

        assertEquals( -1, resource.getSize() );
        assertEquals( -1, resource.getLastModified() );
    }
}
