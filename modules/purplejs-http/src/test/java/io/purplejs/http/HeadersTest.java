package io.purplejs.http;

import java.util.List;

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
        final String value1 = this.headers.get( "mykey" );
        assertNull( value1 );

        this.headers.set( "mykey", "myvalue" );

        final String value2 = this.headers.get( "mykey" );
        assertNotNull( value2 );
        assertEquals( "myvalue", value2 );

        this.headers.remove( "mykey" );

        final String value3 = this.headers.get( "mykey" );
        assertNull( value3 );
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
}
