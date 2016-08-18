package io.purplejs.core.mock

import com.google.common.base.Charsets
import io.purplejs.core.resource.Resource
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class MockResourceLoaderTest
    extends Specification
{
    def MockResourceLoader loader;

    def setup()
    {
        this.loader = new MockResourceLoader();
    }

    def "load not found"()
    {
        when:
        def resource = this.loader.loadOrNull( ResourcePath.from( "/a/b.js" ) );

        then:
        resource == null;
    }

    def "addResource with text"()
    {
        when:
        def resource1 = this.loader.addResource( '/a/hello1.js', 'hello1' );

        then:
        resource1 != null;
        assertResource( '/a/hello1.js', 'hello1' );

        when:
        def resource2 = this.loader.addResource( ResourcePath.from( '/a/hello1.js' ), 'hello1' );

        then:
        resource2 != null;
        assertResource( '/a/hello1.js', 'hello1' );
    }

    def "addResource with bytes"()
    {
        when:
        def resource1 = this.loader.addResource( '/a/hello1.js', 'hello1'.bytes );

        then:
        resource1 != null;
        assertResource( '/a/hello1.js', 'hello1' );

        when:
        def resource2 = this.loader.addResource( ResourcePath.from( '/a/hello1.js' ), 'hello1'.bytes );

        then:
        resource2 != null;
        assertResource( '/a/hello1.js', 'hello1' );
    }

    def assertResource( final String path, final String text )
    {
        final ResourcePath resourcePath = ResourcePath.from( path );
        final Resource resource = this.loader.loadOrNull( resourcePath );

        assert resource != null;
        assert resourcePath == resource.getPath();
        assert text == resource.getBytes().asCharSource( Charsets.UTF_8 ).read();

        return true;
    }
}
