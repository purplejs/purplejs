package io.purplejs.http;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParametersTest
{
    private Parameters params;

    @Before
    public void setUp()
    {
        this.params = new Parameters();
    }

    @Test
    public void get()
    {
        final Collection<String> values1 = this.params.get( "mykey" );
        assertNotNull( values1 );
        assertEquals( 0, values1.size() );

        this.params.put( "mykey", "myvalue" );

        final Collection<String> values2 = this.params.get( "mykey" );
        assertNotNull( values2 );
        assertEquals( 1, values2.size() );
        assertEquals( "myvalue", values2.iterator().next() );

        this.params.remove( "mykey" );

        final Collection<String> values3 = this.params.get( "mykey" );
        assertNotNull( values3 );
        assertEquals( 0, values3.size() );
    }

    @Test
    public void getFirst()
    {
        final Optional<String> value1 = this.params.getFirst( "mykey" );
        assertNotNull( value1 );
        assertFalse( value1.isPresent() );

        this.params.put( "mykey", "myvalue" );

        final Optional<String> value2 = this.params.getFirst( "mykey" );
        assertNotNull( value2 );
        assertTrue( value2.isPresent() );
        assertEquals( "myvalue", value2.get() );

        this.params.remove( "mykey" );

        final Optional<String> value3 = this.params.getFirst( "mykey" );
        assertNotNull( value3 );
        assertFalse( value3.isPresent() );
    }

    @Test
    public void asMap()
    {
        this.params.put( "mykey", "myvalue" );

        final Map<String, Collection<String>> map = this.params.asMap();
        assertNotNull( map );
        assertEquals( 1, map.size() );
        assertEquals( "[myvalue]", map.get( "mykey" ).toString() );
    }
}
