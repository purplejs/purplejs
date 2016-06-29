package io.purplejs.registry;

import java.util.Optional;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegistryTest
{
    public class MyObject1
    {
    }

    public class MyObject2
    {
    }

    public class MyObject3
    {
    }

    private Registry registry;

    @Before
    public void setup()
    {
        this.registry = RegistryBuilder.newBuilder().
            instance( MyObject1.class, new MyObject1() ).
            supplier( MyObject2.class, MyObject2::new ).
            build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstance_notFound()
    {
        this.registry.getInstance( MyObject3.class );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSupplier_notFound()
    {
        this.registry.getSupplier( MyObject3.class );
    }

    @Test
    public void getInstance()
    {
        final MyObject1 instance1 = this.registry.getInstance( MyObject1.class );
        assertNotNull( instance1 );

        final MyObject1 instance2 = this.registry.getInstance( MyObject1.class );
        assertSame( instance1, instance2 );

        final MyObject2 instance3 = this.registry.getInstance( MyObject2.class );
        assertNotNull( instance3 );

        final MyObject2 instance4 = this.registry.getInstance( MyObject2.class );
        assertNotSame( instance3, instance4 );
    }

    @Test
    public void getSupplier()
    {
        final Supplier<MyObject1> supplier1 = this.registry.getSupplier( MyObject1.class );
        assertNotNull( supplier1 );
        assertNotNull( supplier1.get() );
        assertSame( supplier1.get(), supplier1.get() );

        final Supplier<MyObject1> supplier2 = this.registry.getSupplier( MyObject1.class );
        assertNotNull( supplier2 );
        assertSame( supplier1, supplier2 );

        final Supplier<MyObject2> supplier3 = this.registry.getSupplier( MyObject2.class );
        assertNotNull( supplier3 );
        assertNotNull( supplier3.get() );
        assertNotSame( supplier3.get(), supplier3.get() );

        final Supplier<MyObject2> supplier4 = this.registry.getSupplier( MyObject2.class );
        assertNotNull( supplier4 );
        assertSame( supplier3, supplier4 );
    }

    @Test
    public void getOptional()
    {
        final Optional<MyObject1> optional1 = this.registry.getOptional( MyObject1.class );
        assertNotNull( optional1 );
        assertTrue( optional1.isPresent() );
        assertNotNull( optional1.orElse( null ) );

        final Optional<MyObject2> optional2 = this.registry.getOptional( MyObject2.class );
        assertNotNull( optional2 );
        assertTrue( optional2.isPresent() );
        assertNotNull( optional2.orElse( null ) );

        final Optional<MyObject3> optional3 = this.registry.getOptional( MyObject3.class );
        assertNotNull( optional3 );
        assertFalse( optional3.isPresent() );
        assertNull( optional3.orElse( null ) );
    }
}
