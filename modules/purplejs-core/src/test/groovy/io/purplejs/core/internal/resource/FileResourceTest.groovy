package io.purplejs.core.internal.resource

import io.purplejs.core.resource.ResourcePath
import io.purplejs.core.internal.util.IOHelper

class FileResourceTest
    extends ResourceTestSupport
{
    def "accessors"()
    {
        setup:
        def file = writeFile( 'test.txt', 'hello' );
        def path = ResourcePath.from( '/a/b/test.txt' );

        when:
        def resource = new FileResource( path, file );

        then:
        resource.path == path;
        resource.size == file.length();
        resource.lastModified == file.lastModified();
        resource.bytes != null;
        IOHelper.readString( resource.bytes ) == 'hello';
    }
}
