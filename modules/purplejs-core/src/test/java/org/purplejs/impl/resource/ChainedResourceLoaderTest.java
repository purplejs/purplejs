package org.purplejs.impl.resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;
import org.purplejs.resource.ResourceLoader;

import static org.junit.Assert.*;

public class ChainedResourceLoaderTest
{
    private ChainedResourceLoader chain;

    private ResourceLoader loader1;

    private ResourceLoader loader2;

    @Before
    public void setup()
    {
        this.loader1 = Mockito.mock( ResourceLoader.class );
        this.loader2 = Mockito.mock( ResourceLoader.class );
        this.chain = new ChainedResourceLoader( this.loader1, this.loader2 );
    }

    @Test
    public void load_first()
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        final Resource expected = Mockito.mock( Resource.class );

        Mockito.when( this.loader1.load( path ) ).thenReturn( expected );

        final Resource actual = this.chain.load( ResourcePath.from( "/a.txt" ) );
        assertSame( expected, actual );

        Mockito.verify( this.loader1, Mockito.times( 1 ) ).load( path );
        Mockito.verify( this.loader2, Mockito.times( 0 ) ).load( path );
    }

    @Test
    public void load_last()
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        final Resource expected = Mockito.mock( Resource.class );

        Mockito.when( this.loader1.load( path ) ).thenThrow( new ResourceNotFoundException( path ) );
        Mockito.when( this.loader2.load( path ) ).thenReturn( expected );

        final Resource actual = this.chain.load( ResourcePath.from( "/a.txt" ) );
        assertSame( expected, actual );

        Mockito.verify( this.loader1, Mockito.times( 1 ) ).load( path );
        Mockito.verify( this.loader2, Mockito.times( 1 ) ).load( path );
    }
}
