package org.purplejs.impl.resource;

import org.junit.Before;
import org.junit.Test;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;

public class NopResourceLoaderTest
{
    private NopResourceLoader loader;

    @Before
    public void setup()
    {
        this.loader = new NopResourceLoader();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void load()
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        this.loader.load( path );
    }
}
