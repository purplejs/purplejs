package io.purplejs.core.internal.cache

import io.purplejs.core.mock.MockResource
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class ScriptExportsCacheTest
    extends Specification
{
    private ScriptExportsCache cache;

    def setup()
    {
        this.cache = new ScriptExportsCache();
    }

    def "get"()
    {
        setup:
        def resource = mockResource( "/a.txt" );
        def path = ResourcePath.from( "/a.txt" );

        this.cache.put( resource, "value" );

        when:
        def value = this.cache.get( path );

        then:
        value == "value";

        when:
        this.cache.clear();

        then:
        this.cache.get( path ) == null;
    }

    def "get not found"()
    {
        when:
        def value = this.cache.get( ResourcePath.from( "/a.txt" ) );

        then:
        value == null;
    }

    def "isExpired"()
    {
        setup:
        def resource = mockResource( "/a.txt" );

        when:
        this.cache.put( resource, "value" );

        then:
        !this.cache.isExpired();

        when:
        resource.lastModified = System.currentTimeMillis() + 1;

        then:
        this.cache.isExpired();
    }

    private static MockResource mockResource( final String path )
    {
        return new MockResource( ResourcePath.from( path ), "hello".bytes );
    }
}
