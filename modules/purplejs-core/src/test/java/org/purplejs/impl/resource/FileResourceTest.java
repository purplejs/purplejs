package org.purplejs.impl.resource;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.purplejs.resource.ResourcePath;

import com.google.common.base.Charsets;

import static org.junit.Assert.*;

public class FileResourceTest
{
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File file;

    @Before
    public void setup()
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
        final FileResource resource = new FileResource( path, this.file );

        assertEquals( path, resource.getPath() );
        assertEquals( this.file.toURI().toURL(), resource.getUrl() );
        assertEquals( this.file.length(), resource.getSize() );
        assertEquals( this.file.lastModified(), resource.getLastModified() );
        assertNotNull( resource.getBytes() );
        assertEquals( "hello", resource.getBytes().asCharSource( Charsets.UTF_8 ).read() );
    }
}
