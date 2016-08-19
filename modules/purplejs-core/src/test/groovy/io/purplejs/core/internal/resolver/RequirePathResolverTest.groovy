package io.purplejs.core.internal.resolver

import io.purplejs.core.resource.ResourceLoader
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class RequirePathResolverTest
    extends Specification
{
    private ResourceLoader loader;

    def setup()
    {
        this.loader = Mock( ResourceLoader.class );
    }

    private static ResourcePath toPath( final String path )
    {
        return ResourcePath.from( path );
    }

    private void mockExists( final String path )
    {
        this.loader.exists( toPath( path ) ) >> true;
    }

    private ResourcePath resolve( final String dir, final String path )
    {
        return new RequirePathResolver( this.loader, toPath( dir ) ).resolve( path );
    }

    def "resolve file"()
    {
        setup:
        mockExists( "/c.txt" );
        mockExists( "/a/b/c.txt" );
        mockExists( "/a/b/c.js" );
        mockExists( "/a/b/c.json" );

        expect:
        def result = resolve( from, path );
        to != null ? ( result.toString() == to ) : ( result == null );

        where:
        from     | path       | to
        '/a/b'   | '/c.txt'   | '/c.txt'
        '/a/b'   | './c.txt'  | '/a/b/c.txt'
        '/a/b/d' | '../c.txt' | '/a/b/c.txt'
        '/a/b'   | './d.txt'  | null
    }

    def "resolve file js"()
    {
        setup:
        mockExists( "/a/b/c.js" );
        mockExists( "/a/b/c.json" );

        when:
        def path = resolve( '/a/b', './c' );

        then:
        path.toString() == '/a/b/c.js';
    }

    def "resolve file json"()
    {
        setup:
        mockExists( "/a/b/c.json" );

        when:
        def path = resolve( '/a/b', './c' );

        then:
        path.toString() == '/a/b/c.json';
    }

    def "resolve dir js"()
    {
        setup:
        mockExists( "/a/b/c/index.js" );
        mockExists( "/a/b/c/index.json" );

        when:
        def path = resolve( '/a/b', './c' );

        then:
        path.toString() == '/a/b/c/index.js';
    }

    def "resolve dir json"()
    {
        setup:
        mockExists( "/a/b/c/index.json" );

        when:
        def path = resolve( '/a/b', './c' );

        then:
        path.toString() == '/a/b/c/index.json';
    }

    def "resolve search lib"()
    {
        setup:
        mockExists( "/lib/a/b" );
        mockExists( "/lib/a/c.js" );
        mockExists( "/lib/a/d/index.json" );

        expect:
        def result = resolve( from, path );
        to != null ? ( result.toString() == to ) : ( result == null );

        where:
        from   | path  | to
        '/a/b' | 'a/b' | '/lib/a/b'
        '/a/b' | 'a/c' | '/lib/a/c.js'
        '/a/b' | 'a/d' | '/lib/a/d/index.json'
        '/a/b' | 'x'   | null
    }
}
