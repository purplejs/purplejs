package io.purplejs.http.multipart

import com.google.common.collect.Lists
import spock.lang.Specification

class MultipartFormTest
    extends Specification
{
    def "isEmpty"()
    {
        when:
        def form = new MultipartForm();

        then:
        form.isEmpty();

        when:
        form.add( newItem( "item1" ) );

        then:
        !form.isEmpty();
    }

    def "getSize"()
    {
        when:
        def form = new MultipartForm();

        then:
        form.getSize() == 0;

        when:
        form.add( newItem( "item1" ) );
        form.add( newItem( "item2" ) );
        form.add( newItem( "item2" ) );

        then:
        form.getSize() == 3;
    }

    def "test get"()
    {
        setup:
        def form = new MultipartForm();
        def item1 = newItem( "item1" );
        def item2 = newItem( "item2" );
        def item3 = newItem( "item2" );

        form.add( item1 );
        form.add( item2 );
        form.add( item3 );

        when:
        def result = form.get( "unknown" );

        then:
        result == null;

        when:
        result = form.get( "item2" );

        then:
        result == item2;

        when:
        result = form.get( "item2", 1 );

        then:
        result.name == 'item2';

        when:
        result = form.get( "item2", 2 );

        then:
        result == null;
    }

    def "test getAll"()
    {
        setup:
        def form = new MultipartForm();
        form.add( newItem( "item1" ) );
        form.add( newItem( "item2" ) );
        form.add( newItem( "item2" ) );

        when:
        def result = form.getAll( 'unknown' );

        then:
        result != null;
        result.isEmpty();

        when:
        result = form.getAll( 'item2' );

        then:
        result != null;
        result.size() == 2;
    }

    def "test iterate"()
    {
        setup:
        def form = new MultipartForm();
        form.add( newItem( "item1" ) );
        form.add( newItem( "item2" ) );
        form.add( newItem( "item2" ) );

        when:
        def list = Lists.newArrayList( form );

        then:
        list.size() == 3;
    }

    def "test getNames"()
    {
        setup:
        def form = new MultipartForm();
        form.add( newItem( "item1" ) );
        form.add( newItem( "item2" ) );
        form.add( newItem( "item2" ) );

        when:
        def set = form.getNames();

        then:
        set.size() == 2;
    }

    private MultipartItem newItem( final String name )
    {
        final MultipartItem item = Mock( MultipartItem.class );
        item.name >> name;
        return item;
    }
}
