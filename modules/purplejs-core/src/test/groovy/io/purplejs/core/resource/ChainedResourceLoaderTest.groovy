package io.purplejs.core.resource

import spock.lang.Specification

class ChainedResourceLoaderTest
    extends Specification
{
    private ChainedResourceLoader chain;

    private ResourceLoader loader1;

    private ResourceLoader loader2;

    def setup()
    {
        this.loader1 = Mock( ResourceLoader.class );
        this.loader2 = Mock( ResourceLoader.class );
        this.chain = new ChainedResourceLoader( this.loader1, this.loader2 );
    }

    def "load first"()
    {
        setup:
        def path = ResourcePath.from( "/a.txt" );
        def expected = Mock( Resource.class );
        this.loader1.loadOrNull( path ) >> expected;

        when:
        def actual = this.chain.load( path );

        then:
        actual == expected;
    }

    def "load second"()
    {
        setup:
        def path = ResourcePath.from( "/a.txt" );
        def expected = Mock( Resource.class );
        this.loader2.loadOrNull( path ) >> expected;

        when:
        def actual = this.chain.load( path );

        then:
        actual == expected;
    }
}
