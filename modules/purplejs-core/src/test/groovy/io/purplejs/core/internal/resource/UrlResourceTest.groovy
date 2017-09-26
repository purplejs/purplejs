package io.purplejs.core.internal.resource

import io.purplejs.core.resource.ResourcePath
import io.purplejs.core.internal.util.IOHelper

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
        IOHelper.readString( resource.bytes ) == 'hello';
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
