package io.purplejs.boot.internal.request

import com.google.common.collect.Lists
import io.purplejs.http.MultipartForm
import spock.lang.Specification

import javax.servlet.http.Part

class MultipartFormImplTest
    extends Specification
{
    def "isEmpty"()
    {
        when:
        def form = newForm();

        then:
        form.isEmpty();

        when:
        form = newForm( newPart( "item1" ) );

        then:
        !form.isEmpty();
    }

    def "getSize"()
    {
        when:
        def form = newForm();

        then:
        form.getSize() == 0;

        when:
        form = newForm( newPart( "item1" ), newPart( "item2" ) );

        then:
        form.getSize() == 2;
    }

    def "test delete"()
    {
        setup:
        def part1 = newPart( "part1" );
        def part2 = newPart( "part2" );
        def form = newForm( part1, part2 );

        when:
        form.delete();

        then:
        1 * part1.delete();
        1 * part2.delete();
    }

    def "test get"()
    {
        setup:
        def part1 = newPart( "part1" );
        def part2 = newPart( "part2" );

        when:
        def form = newForm( part1, part2 );

        then:
        !form.get( 'unknown' ).isPresent();
        form.get( "part1" ).get() != null;
        form.get( "part2" ).get() != null;
    }

    def "test iterate"()
    {
        setup:
        def form = newForm( newPart( "file" ), newPart( "file" ) );

        when:
        def iterator = form.iterator();

        then:
        Lists.newArrayList( iterator ).size() == 2;
    }

    private static MultipartForm newForm( final Part... parts )
    {
        return new MultipartFormImpl( Lists.newArrayList( parts ) );
    }

    private Part newPart( final String name )
    {
        final Part part = Mock( Part.class );
        part.name >> name;
        return part;
    }
}
