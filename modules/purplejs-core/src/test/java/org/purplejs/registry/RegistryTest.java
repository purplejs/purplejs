package org.purplejs.registry;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import static org.junit.Assert.*;

public class RegistryTest
{
    private Registry registry;

    @Before
    public void setup()
    {
        final AtomicInteger counter = new AtomicInteger( 0 );

        this.registry = RegistryBuilder.create().
            instance( String.class, "Hello1" ).
            instance( String.class, "Hello2" ).
            supplier( Integer.class, counter::incrementAndGet ).
            build();
    }

    @Test
    public void get()
    {
        assertEquals( "Hello2", this.registry.get( String.class ) );
        assertEquals( new Integer( 1 ), this.registry.get( Integer.class ) );
        assertEquals( new Integer( 2 ), this.registry.get( Integer.class ) );
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_notFound()
    {
        this.registry.get( Float.class );
    }

    @Test
    public void getBinding()
    {
        final Binding<String> binding1 = this.registry.getBinding( String.class );
        assertNotNull( binding1 );
        assertFalse( binding1.isEmpty() );
        assertEquals( "Hello2", binding1.get() );
        assertNotNull( binding1.iterator() );
        assertTrue( binding1.iterator().hasNext() );

        final Binding<Float> binding2 = this.registry.getBinding( Float.class );
        assertNotNull( binding2 );
        assertTrue( binding2.isEmpty() );
        assertNotNull( binding2.iterator() );
        assertFalse( binding2.iterator().hasNext() );
    }

    @Test
    public void getAll()
    {
        final Iterable<String> it1 = this.registry.getAll( String.class );
        assertNotNull( it1 );

        final Iterator<String> it2 = it1.iterator();
        assertEquals( "Hello2", it2.next() );
        assertEquals( "Hello1", it2.next() );
        assertFalse( it2.hasNext() );
    }

    @Test
    public void getOptional()
    {
        final Optional<String> opt1 = this.registry.getOptional( String.class );
        assertNotNull( opt1 );
        assertTrue( opt1.isPresent() );
        assertEquals( "Hello2", opt1.get() );

        final Optional<Float> opt2 = this.registry.getOptional( Float.class );
        assertNotNull( opt2 );
        assertFalse( opt2.isPresent() );
    }

    @Test
    public void getBindings()
    {
        final Iterable<Binding> bindings = this.registry.getBindings();
        assertNotNull( bindings );

        final List<Binding> list = Lists.newArrayList( bindings );
        assertEquals( 2, list.size() );
    }

    @Test
    public void getSupplier()
    {
        final Supplier<Integer> supplier1 = this.registry.getSupplier( Integer.class );
        assertNotNull( supplier1 );
        assertEquals( new Integer( 1 ), supplier1.get() );

        final Supplier<Float> supplier2 = this.registry.getSupplier( Float.class );
        assertNotNull( supplier2 );
    }
}
