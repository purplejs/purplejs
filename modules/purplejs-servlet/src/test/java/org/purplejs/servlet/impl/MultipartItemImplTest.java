package org.purplejs.servlet.impl;

import java.io.ByteArrayInputStream;

import javax.servlet.http.Part;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import static org.junit.Assert.*;

public class MultipartItemImplTest
{
    private Part part;

    private MultipartItemImpl item;

    @Before
    public void setup()
    {
        this.part = Mockito.mock( Part.class );
        this.item = new MultipartItemImpl( this.part );
    }

    @Test
    public void getName()
    {
        Mockito.when( this.part.getName() ).thenReturn( "item" );

        final String name = this.item.getName();
        assertEquals( "item", name );
    }

    @Test
    public void getFileName()
    {
        Mockito.when( this.part.getSubmittedFileName() ).thenReturn( "file.txt" );

        final String name = this.item.getFileName();
        assertEquals( "file.txt", name );
    }

    @Test
    public void getSize()
    {
        Mockito.when( this.part.getSize() ).thenReturn( 10L );

        final long size = this.item.getSize();
        assertEquals( 10L, size );
    }

    @Test
    public void getContentType()
    {
        Mockito.when( this.part.getContentType() ).thenReturn( "text/plain" );

        final MediaType type = this.item.getContentType();
        assertNotNull( type );
        assertEquals( "text/plain", type.toString() );
    }

    @Test
    public void getBytes()
        throws Exception
    {
        final ByteArrayInputStream in = new ByteArrayInputStream( new byte[2] );
        Mockito.when( this.part.getInputStream() ).thenReturn( in );
        Mockito.when( this.part.getSize() ).thenReturn( 2L );

        final ByteSource value = this.item.getBytes();
        assertNotNull( value );
        assertEquals( 2L, value.size() );
    }

    @Test
    public void getAsString()
        throws Exception
    {
        final ByteArrayInputStream in = new ByteArrayInputStream( "hello".getBytes() );
        Mockito.when( this.part.getInputStream() ).thenReturn( in );

        final String value = this.item.getAsString();
        assertNotNull( value );
        assertEquals( "hello", value );
    }

    @Test
    public void delete()
        throws Exception
    {
        this.item.delete();
        Mockito.verify( this.part, Mockito.times( 1 ) ).delete();
    }
}
