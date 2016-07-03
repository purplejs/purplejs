package io.purplejs.impl.resource;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourceNotFoundException;
import io.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class ClassLoaderResourceLoaderTest
{
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ClassLoaderResourceLoader loader;

    private ClassLoaderResourceLoader loaderWithPrefix;

    @Before
    public void setUp()
        throws Exception
    {
        final ResourceTestFixture fixture = new ResourceTestFixture( this.temporaryFolder );
        fixture.createFiles();

        final ClassLoader classLoader = new URLClassLoader( new URL[]{fixture.getRootDir().toURI().toURL()} );

        this.loader = new ClassLoaderResourceLoader( classLoader, null );
        this.loaderWithPrefix = new ClassLoaderResourceLoader( classLoader, "/a" );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void load_not_found()
    {
        this.loader.load( ResourcePath.from( "/a/x.txt" ) );
    }

    @Test
    public void load_found()
    {
        final Resource resource = this.loader.load( ResourcePath.from( "/a/b.txt" ) );
        assertNotNull( resource );
    }

    @Test
    public void load_with_prefix()
    {
        final Resource resource = this.loaderWithPrefix.load( ResourcePath.from( "/b.txt" ) );
        assertNotNull( resource );
    }
}
