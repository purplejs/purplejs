package io.purplejs.core.resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import io.purplejs.core.exception.NotFoundException;

import static org.junit.Assert.*;

public class FileResourceLoaderTest
{
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private FileResourceLoader loader;

    @Before
    public void setUp()
        throws Exception
    {
        final ResourceTestFixture fixture = new ResourceTestFixture( this.temporaryFolder );
        fixture.createFiles();

        this.loader = new FileResourceLoader( fixture.getRootDir() );
    }

    @Test(expected = NotFoundException.class)
    public void load_not_found()
    {
        this.loader.load( ResourcePath.from( "/a/x.txt" ) );
    }

    @Test(expected = NotFoundException.class)
    public void load_directory()
    {
        this.loader.load( ResourcePath.from( "/a" ) );
    }

    @Test
    public void load_found()
    {
        final Resource resource = this.loader.load( ResourcePath.from( "/a/b.txt" ) );
        assertNotNull( resource );
    }
}
