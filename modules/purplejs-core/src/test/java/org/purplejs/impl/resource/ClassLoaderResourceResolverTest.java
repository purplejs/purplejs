package org.purplejs.impl.resource;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class ClassLoaderResourceResolverTest
{
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ClassLoaderResourceResolver resolver;

    private ClassLoaderResourceResolver resolverWithPrefix;

    @Before
    public void setup()
        throws Exception
    {
        final ResourceTestFixture fixture = new ResourceTestFixture( this.temporaryFolder );
        fixture.createFiles();

        final ClassLoader classLoader = new URLClassLoader( new URL[]{fixture.getRootDir().toURI().toURL()} );

        this.resolver = new ClassLoaderResourceResolver( classLoader, null );
        this.resolverWithPrefix = new ClassLoaderResourceResolver( classLoader, "/a" );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void resolve_not_found()
    {
        this.resolver.resolve( ResourcePath.from( "/a/x.txt" ) );
    }

    @Test
    public void resolve_found()
    {
        final Resource resource = this.resolver.resolve( ResourcePath.from( "/a/b.txt" ) );
        assertNotNull( resource );
    }

    @Test
    public void resolve_with_prefix()
    {
        final Resource resource = this.resolverWithPrefix.resolve( ResourcePath.from( "/b.txt" ) );
        assertNotNull( resource );
    }
}
