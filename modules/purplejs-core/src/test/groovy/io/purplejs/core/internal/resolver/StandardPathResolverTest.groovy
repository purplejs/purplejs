package io.purplejs.core.internal.resolver

import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class StandardPathResolverTest
    extends Specification
{
    private static ResourcePath resolve( final String dir, final String path )
    {
        return new StandardPathResolver( ResourcePath.from( dir ) ).resolve( path );
    }

    def "test resolve"()
    {
        expect:
        def result = resolve( from, path );
        result.toString() == to;

        where:
        from   | path          | to
        '/a/b' | 'test.txt'    | '/a/b/test.txt'
        '/a/b' | './test.txt'  | '/a/b/test.txt'
        '/a/b' | '../test.txt' | '/a/test.txt'
        '/a/b' | '/test.txt'   | '/test.txt'
    }
}
