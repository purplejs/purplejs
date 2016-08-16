package io.purplejs.core.resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ChainedResourceLoaderTest
{
    private ChainedResourceLoader chain;

    private ResourceLoader loader1;

    private ResourceLoader loader2;

    @Before
    public void setUp()
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

        Mockito.when( this.loader1.loadOrNull( path ) ).thenReturn( expected );

        final Resource actual = this.chain.load( ResourcePath.from( "/a.txt" ) );
        assertSame( expected, actual );

        Mockito.verify( this.loader1, Mockito.times( 1 ) ).loadOrNull( path );
        Mockito.verify( this.loader2, Mockito.times( 0 ) ).loadOrNull( path );
    }

    @Test
    public void load_last()
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        final Resource expected = Mockito.mock( Resource.class );

        Mockito.when( this.loader1.loadOrNull( path ) ).thenReturn( null );
        Mockito.when( this.loader2.loadOrNull( path ) ).thenReturn( expected );

        final Resource actual = this.chain.load( ResourcePath.from( "/a.txt" ) );
        assertSame( expected, actual );

        Mockito.verify( this.loader1, Mockito.times( 1 ) ).loadOrNull( path );
        Mockito.verify( this.loader2, Mockito.times( 1 ) ).loadOrNull( path );
    }
}
