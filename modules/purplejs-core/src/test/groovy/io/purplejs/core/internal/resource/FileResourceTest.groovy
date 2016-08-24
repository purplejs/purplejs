package io.purplejs.core.internal.resource

import com.google.common.base.Charsets
import io.purplejs.core.resource.ResourcePath

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
        resource.bytes.asCharSource( Charsets.UTF_8 ).read() == 'hello';
    }
}
