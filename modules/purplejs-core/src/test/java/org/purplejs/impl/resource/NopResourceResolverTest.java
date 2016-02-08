package org.purplejs.impl.resource;

import org.junit.Before;
import org.junit.Test;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;

public class NopResourceResolverTest
{
    private NopResourceResolver resolver;

    @Before
    public void setup()
    {
        this.resolver = new NopResourceResolver();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void resolve()
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        this.resolver.resolve( path );
    }
}
