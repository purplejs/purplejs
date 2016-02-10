package org.purplejs.http;

import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttributesTest
{
    public class TypedValue
    {
    }

    private Attributes attrs;

    @Before
    public void setup()
    {
        this.attrs = new Attributes();
    }

    @Test
    public void untyped_access()
    {
        final Optional<Object> value1 = this.attrs.get( "mykey" );
        assertNotNull( value1 );
        assertFalse( value1.isPresent() );

        this.attrs.set( "mykey", "myvalue" );

        final Optional<Object> value2 = this.attrs.get( "mykey" );
        assertNotNull( value2 );
        assertTrue( value2.isPresent() );
        assertEquals( "myvalue", value2.get() );

        this.attrs.remove( "mykey" );

        final Optional<Object> value3 = this.attrs.get( "mykey" );
        assertNotNull( value3 );
        assertFalse( value3.isPresent() );
    }

    @Test
    public void typed_access()
    {
        final Optional<TypedValue> value1 = this.attrs.get( TypedValue.class );
        assertNotNull( value1 );
        assertFalse( value1.isPresent() );

        final TypedValue typedValue = new TypedValue();
        this.attrs.set( TypedValue.class, typedValue );

        final Optional<TypedValue> value2 = this.attrs.get( TypedValue.class );
        assertNotNull( value2 );
        assertTrue( value2.isPresent() );
        assertSame( typedValue, value2.get() );

        this.attrs.remove( TypedValue.class );

        final Optional<TypedValue> value3 = this.attrs.get( TypedValue.class );
        assertNotNull( value3 );
        assertFalse( value3.isPresent() );
    }

    @Test
    public void asMap()
    {
        final TypedValue typedValue = new TypedValue();

        this.attrs.set( "mykey", "myvalue" );
        this.attrs.set( TypedValue.class, typedValue );

        final Map<String, Object> map = this.attrs.asMap();
        assertNotNull( map );
        assertEquals( 2, map.size() );
        assertEquals( "myvalue", map.get( "mykey" ) );
        assertSame( typedValue, map.get( TypedValue.class.getName() ) );
    }
}
