package io.purplejs.core.internal.cache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourcePath;

import static org.junit.Assert.*;

public class ScriptExportsCacheTest
{
    private ScriptExportsCache cache;

    @Before
    public void setUp()
    {
        this.cache = new ScriptExportsCache();
    }

    @Test
    public void get()
    {
        final Object value1 = this.cache.get( ResourcePath.from( "/a.txt" ) );
        assertNull( value1 );

        final Resource resource = mockResource( "/a.txt" );
        this.cache.put( resource, "value" );

        final Object value2 = this.cache.get( ResourcePath.from( "/a.txt" ) );
        assertEquals( "value", value2 );
    }

    @Test
    public void isExpired()
    {
        assertEquals( false, this.cache.isExpired() );

        final Resource resource = mockResource( "/a.txt" );
        this.cache.put( resource, "value" );
        assertEquals( false, this.cache.isExpired() );

        Mockito.when( resource.getLastModified() ).thenReturn( System.currentTimeMillis() + 1 );
        assertEquals( true, this.cache.isExpired() );
    }

    private Resource mockResource( final String path )
    {
        final Resource resource = Mockito.mock( Resource.class );
        Mockito.when( resource.getPath() ).thenReturn( ResourcePath.from( path ) );
        return resource;
    }
}
