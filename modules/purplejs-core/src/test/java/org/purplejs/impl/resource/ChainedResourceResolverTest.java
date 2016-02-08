package org.purplejs.impl.resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;
import org.purplejs.resource.ResourceResolver;

import static org.junit.Assert.*;

public class ChainedResourceResolverTest
{
    private ChainedResourceResolver chain;

    private ResourceResolver resolver1;

    private ResourceResolver resolver2;

    @Before
    public void setup()
    {
        this.resolver1 = Mockito.mock( ResourceResolver.class );
        this.resolver2 = Mockito.mock( ResourceResolver.class );
        this.chain = new ChainedResourceResolver( this.resolver1, this.resolver2 );
    }

    @Test
    public void resolve_first()
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        final Resource expected = Mockito.mock( Resource.class );

        Mockito.when( this.resolver1.resolve( path ) ).thenReturn( expected );

        final Resource actual = this.chain.resolve( ResourcePath.from( "/a.txt" ) );
        assertSame( expected, actual );

        Mockito.verify( this.resolver1, Mockito.times( 1 ) ).resolve( path );
        Mockito.verify( this.resolver2, Mockito.times( 0 ) ).resolve( path );
    }

    @Test
    public void resolve_last()
    {
        final ResourcePath path = ResourcePath.from( "/a.txt" );
        final Resource expected = Mockito.mock( Resource.class );

        Mockito.when( this.resolver1.resolve( path ) ).thenThrow( new ResourceNotFoundException( path ) );
        Mockito.when( this.resolver2.resolve( path ) ).thenReturn( expected );

        final Resource actual = this.chain.resolve( ResourcePath.from( "/a.txt" ) );
        assertSame( expected, actual );

        Mockito.verify( this.resolver1, Mockito.times( 1 ) ).resolve( path );
        Mockito.verify( this.resolver2, Mockito.times( 1 ) ).resolve( path );
    }
}
