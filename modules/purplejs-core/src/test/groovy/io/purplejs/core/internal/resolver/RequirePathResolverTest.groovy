package io.purplejs.core.internal.resolver

import io.purplejs.core.resource.ResourceLoader
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class RequirePathResolverTest
    extends Specification
{
    private ResourceLoader loader

    private ResourcePathResolver resolver

    def setup()
    {
        this.loader = Mock( ResourceLoader.class )
        this.resolver = new RequirePathResolver( this.loader )
    }

    private static ResourcePath toPath( final String path )
    {
        return ResourcePath.from( path )
    }

    private void mockExists( final String path )
    {
        this.loader.exists( toPath( path ) ) >> true
    }

    private ResourcePathResult resolve( final String dir, final String path )
    {
        return this.resolver.resolve( toPath( dir ), path )
    }

    def "resolve file"()
    {
        setup:
        mockExists( "/c.txt" )
        mockExists( "/a/b/c.txt" )
        mockExists( "/a/b/c.js" )
        mockExists( "/a/b/c.json" )

        expect:
        def result = resolve( from, path )
        def found = result.found
        to != null ? ( found.toString() == to ) : ( found == null )
        scanned == result.scanned.collect { it.toString() }

        where:
        from     | path       | to           | scanned
        '/a/b'   | '/c.txt'   | '/c.txt'     | ['/c.txt']
        '/a/b'   | './c.txt'  | '/a/b/c.txt' | ['/a/b/c.txt']
        '/a/b/d' | '../c.txt' | '/a/b/c.txt' | ['/a/b/c.txt']
        '/a/b'   | './d.txt'  | null         |
            ['/a/b/d.txt', '/a/b/d.txt.js', '/a/b/d.txt.json', '/a/b/d.txt/index.js', '/a/b/d.txt/index.json']
    }

    def "resolve file js"()
    {
        setup:
        mockExists( "/a/b/c.js" )
        mockExists( "/a/b/c.json" )

        when:
        def result = resolve( '/a/b', './c' )
        def path = result.found
        def scanned = result.scanned.collect { it.toString() }

        then:
        path.toString() == '/a/b/c.js'
        scanned == ['/a/b/c', '/a/b/c.js']
    }

    def "resolve file json"()
    {
        setup:
        mockExists( "/a/b/c.json" )

        when:
        def result = resolve( '/a/b', './c' )
        def path = result.found
        def scanned = result.scanned.collect { it.toString() }

        then:
        path.toString() == '/a/b/c.json'
        scanned == ['/a/b/c', '/a/b/c.js', '/a/b/c.json']
    }

    def "resolve dir js"()
    {
        setup:
        mockExists( "/a/b/c/index.js" )
        mockExists( "/a/b/c/index.json" )

        when:
        def result = resolve( '/a/b', './c' )
        def path = result.found
        def scanned = result.scanned.collect { it.toString() }

        then:
        path.toString() == '/a/b/c/index.js'
        scanned == ['/a/b/c', '/a/b/c.js', '/a/b/c.json', '/a/b/c/index.js']
    }

    def "resolve dir json"()
    {
        setup:
        mockExists( "/a/b/c/index.json" )

        when:
        def result = resolve( '/a/b', './c' )
        def path = result.found
        def scanned = result.scanned.collect { it.toString() }

        then:
        path.toString() == '/a/b/c/index.json'
        scanned == ['/a/b/c', '/a/b/c.js', '/a/b/c.json', '/a/b/c/index.js', '/a/b/c/index.json']
    }

    def "resolve search lib"()
    {
        setup:
        mockExists( "/lib/a/b" )
        mockExists( "/lib/a/c.js" )
        mockExists( "/lib/a/d/index.json" )

        expect:
        def result = resolve( from, path )
        def found = result.found
        to != null ? ( found.toString() == to ) : ( found == null )
        scanned == result.scanned.collect { it.toString() }

        where:
        from   | path  | to                    | scanned
        '/a/b' | 'a/b' | '/lib/a/b'            | ['/lib/a/b']
        '/a/b' | 'a/c' | '/lib/a/c.js'         | ['/lib/a/c', '/lib/a/c.js']
        '/a/b' | 'a/d' | '/lib/a/d/index.json' | ['/lib/a/d', '/lib/a/d.js', '/lib/a/d.json', '/lib/a/d/index.js', '/lib/a/d/index.json']
        '/a/b' | 'x'   | null                  | ['/lib/x', '/lib/x.js', '/lib/x.json', '/lib/x/index.js', '/lib/x/index.json']
    }
}
