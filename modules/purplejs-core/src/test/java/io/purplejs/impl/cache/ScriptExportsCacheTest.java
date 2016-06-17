package io.purplejs.impl.cache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class ScriptExportsCacheTest
{
    private ScriptExportsCache cache;

    @Before
    public void setup()
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
        final Resource resource = mockResource( "/a.txt" );
        assertEquals( true, this.cache.isExpired( resource ) );

        this.cache.put( resource, "value" );
        assertEquals( false, this.cache.isExpired( resource ) );

        Mockito.when( resource.getLastModified() ).thenReturn( System.currentTimeMillis() + 1 );
        assertEquals( true, this.cache.isExpired( resource ) );
    }

    private Resource mockResource( final String path )
    {
        final Resource resource = Mockito.mock( Resource.class );
        Mockito.when( resource.getPath() ).thenReturn( ResourcePath.from( path ) );
        return resource;
    }
}
