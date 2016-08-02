package io.purplejs.impl.resource;

import org.junit.Before;
import org.junit.Test;

import io.purplejs.exception.NotFoundException;
import io.purplejs.resource.ResourcePath;

public class NopResourceLoaderTest
{
    private NopResourceLoader loader;

    @Before
    public void setUp()
    {
        this.loader = new NopResourceLoader();
    }

    @Test(expected = NotFoundException.class)
    public void load()
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        this.loader.load( path );
    }
}
