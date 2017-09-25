package io.purplejs.core.internal.resolver

import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class StandardPathResolverTest
    extends Specification
{
    private ResourcePathResolver resolver

    def setup()
    {
        this.resolver = new StandardPathResolver()
    }

    private ResourcePathResult resolve( final String dir, final String path )
    {
        return this.resolver.resolve( ResourcePath.from( dir ), path )
    }

    def "test resolve"()
    {
        expect:
        def result = resolve( from, path )
        result.found.toString() == to

        where:
        from   | path          | to
        '/a/b' | 'test.txt'    | '/a/b/test.txt'
        '/a/b' | './test.txt'  | '/a/b/test.txt'
        '/a/b' | '../test.txt' | '/a/test.txt'
        '/a/b' | '/test.txt'   | '/test.txt'
    }
}
