package org.purplejs.registry;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegistryBuilderTest
{
    /*
    @Test
    public void join()
    {
        final Registry reg1 = RegistryBuilder.create().
            instance( String.class, "Hello1" ).
            build();

        final Registry reg2 = RegistryBuilder.create().
            instance( String.class, "Hello2" ).
            instance( Integer.class, 11 ).
            join( reg1 ).
            build();

        final Registry reg3 = RegistryBuilder.create().
            join( reg1 ).
            instance( String.class, "Hello3" ).
            build();

        assertNotNull( reg1 );
        assertEquals( "Hello1", reg1.get( String.class ) );

        assertNotNull( reg2 );
        assertEquals( "Hello1", reg2.get( String.class ) );
        assertEquals( new Integer( 11 ), reg2.get( Integer.class ) );

        assertNotNull( reg3 );
        assertEquals( "Hello3", reg3.get( String.class ) );
    }
    */

    @Test
    public void register()
    {
        final AtomicInteger counter = new AtomicInteger( 0 );

        final Registry reg = RegistryBuilder.create().
            instance( String.class, "Hello" ).
            supplier( Integer.class, counter::incrementAndGet ).
            build();

        assertNotNull( reg );
        assertEquals( "Hello", reg.get( String.class ) );
        assertEquals( new Integer( 1 ), reg.get( Integer.class ) );
        assertEquals( new Integer( 2 ), reg.get( Integer.class ) );
    }
}
