package io.purplejs.http;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

import static org.junit.Assert.*;

public class HeadersTest
{
    private Headers headers;

    @Before
    public void setUp()
    {
        this.headers = new Headers();
    }

    @Test
    public void get_set_remove()
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
    public void getAccept()
    {
        final List<MediaType> list1 = this.headers.getAccept();
        assertEquals( 0, list1.size() );

        this.headers.set( HttpHeaders.ACCEPT, "text/html,application/json" );
        final List<MediaType> list2 = this.headers.getAccept();
        assertEquals( 2, list2.size() );
        assertEquals( "[text/html, application/json]", list2.toString() );
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
}
