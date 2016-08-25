package io.purplejs.boot.internal.request

import com.google.common.collect.Lists
import spock.lang.Specification

import javax.servlet.http.Part

class MultipartFormFactoryTest
    extends Specification
{
    def MultipartFormFactory factory;

    def setup()
    {
        this.factory = new MultipartFormFactory();
    }

    def "empty form"()
    {
        when:
        def form = this.factory.create( Lists.newArrayList() );

        then:
        form.empty;
    }

    def "create form"()
    {
        setup:
        def part1 = mockPart( 'part1' );
        def part2 = mockPart( 'part2' );

        when:
        def form = this.factory.create( Lists.newArrayList( part1, part2 ) );

        then:
        !form.empty;
        form.get( 'part1' ) != null;
        form.get( 'part2' ) != null;
    }

    private Part mockPart( final String name )
    {
        final Part part = Mock( Part.class );
        part.name >> name;
        return part;
    }
}
