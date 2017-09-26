package io.purplejs.core.internal.resolver

import io.purplejs.core.resource.ResourceResolverBuilder
import io.purplejs.core.resource.ResourceResolverMode

class ResourceResolver_simpleTest
    extends AbstractResourceResolverTest
{
    @Override
    protected void configure()
    {
        this.mode = ResourceResolverMode.SIMPLE
        this.resolver = ResourceResolverBuilder.newBuilder().build()
    }

    def "test resolve"()
    {
        expect:
        def result = resolve( from, path )
        result.toString() == to

        where:
        from   | path          | to
        '/a/b' | 'test.txt'    | '/a/b/test.txt'
        '/a/b' | './test.txt'  | '/a/b/test.txt'
        '/a/b' | '../test.txt' | '/a/test.txt'
        '/a/b' | '/test.txt'   | '/test.txt'
    }
}
