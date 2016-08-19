package io.purplejs.core.resource

import com.google.common.base.Charsets

class UrlResourceTest
    extends ResourceTestSupport
{
    def "accessors"()
    {
        setup:
        def file = writeFile( 'test.txt', 'hello' );
        def path = ResourcePath.from( '/a/b/test.txt' );

        when:
        def resource = new UrlResource( path, file.toURI().toURL() );

        then:
        resource.path == path;
        resource.size == file.length();
        resource.lastModified == file.lastModified();
        resource.bytes != null;
        resource.bytes.asCharSource( Charsets.UTF_8 ).read() == 'hello';
    }

    def "url problem"()
    {
        setup:
        def path = ResourcePath.from( '/a/b/test.txt' );

        when:
        def resource = new UrlResource( path, new URL( 'file:not-found' ) );

        then:
        resource.size == -1;
        resource.lastModified == -1;
    }
}
