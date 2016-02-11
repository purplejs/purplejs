package org.purplejs.servlet.impl;

import java.util.Iterator;

import javax.servlet.http.Part;

import org.junit.Test;
import org.mockito.Mockito;
import org.purplejs.http.MultipartForm;
import org.purplejs.http.MultipartItem;

import com.google.common.collect.Lists;

import static org.junit.Assert.*;

public class MultipartFormImplTest
{
    @Test
    public void isEmpty()
    {
        final MultipartForm form1 = newForm();
        assertTrue( form1.isEmpty() );

        final MultipartForm form2 = newForm( newPart( "item1" ) );
        assertFalse( form2.isEmpty() );
    }

    @Test
    public void getSize()
    {
        final MultipartForm form1 = newForm();
        assertEquals( 0, form1.getSize() );

        final MultipartForm form2 = newForm( newPart( "item1" ), newPart( "item2" ) );
        assertEquals( 2, form2.getSize() );
    }

    @Test
    public void delete()
        throws Exception
    {
        final Part part1 = newPart( "item1" );
        final Part part2 = newPart( "item2" );

        final MultipartForm form = newForm( part1, part2 );
        form.delete();

        Mockito.verify( part1, Mockito.times( 1 ) ).delete();
        Mockito.verify( part2, Mockito.times( 1 ) ).delete();
    }

    @Test
    public void get()
    {
        final Part part1 = newPart( "item1" );
        final Part part2 = newPart( "item2" );

        final MultipartForm form = newForm( part1, part2 );
        assertFalse( form.get( "unknown" ).isPresent() );
        assertNotNull( form.get( "item1" ).get() );
        assertNotNull( form.get( "item2" ).get() );
    }

    @Test
    public void iterate()
    {
        final MultipartForm form = newForm( newPart( "file" ), newPart( "file" ) );
        final Iterator<MultipartItem> it = form.iterator();

        assertTrue( it.hasNext() );
        assertNotNull( it.next() );

        assertTrue( it.hasNext() );
        assertNotNull( it.next() );

        assertFalse( it.hasNext() );
    }

    private MultipartForm newForm( final Part... parts )
    {
        return new MultipartFormImpl( Lists.newArrayList( parts ) );
    }

    private Part newPart( final String name )
    {
        final Part part = Mockito.mock( Part.class );
        Mockito.when( part.getName() ).thenReturn( name );
        return part;
    }
}
