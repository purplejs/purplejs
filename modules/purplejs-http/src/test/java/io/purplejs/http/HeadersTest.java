package io.purplejs.http;

import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.google.common.net.MediaType;

import static org.junit.Assert.*;

public class HeadersTest
{
    private Headers headers;

    @Before
    public void setup()
    {
        this.headers = new Headers();
    }

    @Test
    public void access()
    {
        final Optional<String> value1 = this.headers.get( "mykey" );
        assertNotNull( value1 );
        assertFalse( value1.isPresent() );

        this.headers.set( "mykey", "myvalue" );

        final Optional<String> value2 = this.headers.get( "mykey" );
        assertNotNull( value2 );
        assertTrue( value2.isPresent() );
        assertEquals( "myvalue", value2.get() );

        this.headers.remove( "mykey" );

        final Optional<String> value3 = this.headers.get( "mykey" );
        assertNotNull( value3 );
        assertFalse( value3.isPresent() );
    }

    @Test
    public void asMap()
    {
        this.headers.set( "mykey", "myvalue" );

        final Map<String, String> map = this.headers.asMap();
        assertNotNull( map );
        assertEquals( 1, map.size() );
        assertEquals( "myvalue", map.get( "mykey" ) );
    }

    @Test
    public void getContentType()
    {
        final Optional<MediaType> type1 = this.headers.getContentType();
        assertNotNull( type1 );
        assertFalse( type1.isPresent() );

        this.headers.set( "Content-Type", "text/plain" );

        final Optional<MediaType> type2 = this.headers.getContentType();
        assertNotNull( type2 );
        assertTrue( type2.isPresent() );
        assertEquals( "text/plain", type2.get().toString() );
    }
}
