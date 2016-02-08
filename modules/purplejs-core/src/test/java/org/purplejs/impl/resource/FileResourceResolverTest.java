package org.purplejs.impl.resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class FileResourceResolverTest
{
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private FileResourceResolver resolver;

    @Before
    public void setup()
        throws Exception
    {
        final ResourceTestFixture fixture = new ResourceTestFixture( this.temporaryFolder );
        fixture.createFiles();

        this.resolver = new FileResourceResolver( fixture.getRootDir() );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void resolve_not_found()
    {
        this.resolver.resolve( ResourcePath.from( "/a/x.txt" ) );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void resolve_directory()
    {
        this.resolver.resolve( ResourcePath.from( "/a" ) );
    }

    @Test
    public void resolve_found()
    {
        final Resource resource = this.resolver.resolve( ResourcePath.from( "/a/b.txt" ) );
        assertNotNull( resource );
    }
}
