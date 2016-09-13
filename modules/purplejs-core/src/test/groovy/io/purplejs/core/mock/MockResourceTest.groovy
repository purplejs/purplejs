package io.purplejs.core.mock

import com.google.common.base.Charsets
import io.purplejs.core.resource.ResourcePath
import io.purplejs.core.util.IOHelper
import spock.lang.Specification

class MockResourceTest
    extends Specification
{
    def "accessors"()
    {
        when:
        def path = ResourcePath.from( "/a/b.js" );
        def resource = new MockResource( path, "hello".getBytes( Charsets.UTF_8 ) );

        then:
        resource.path == path;
        resource.size == 5;
        resource.lastModified > 0;
        IOHelper.readString( resource.bytes ) == 'hello';

        when:
        resource.lastModified = 0;

        then:
        resource.lastModified == 0
    }
}
