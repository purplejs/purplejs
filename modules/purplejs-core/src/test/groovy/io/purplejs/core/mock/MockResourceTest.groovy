package io.purplejs.core.mock

import com.google.common.base.Charsets
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class MockResourceTest
    extends Specification
{
    def "getters"()
    {
        when:
        def path = ResourcePath.from( "/a/b.js" );
        def resource = new MockResource( path, "hello".getBytes( Charsets.UTF_8 ) );

        then:
        resource.getPath() == path;
        resource.getSize() == 5;
        resource.getLastModified() > 0;
        resource.getBytes().asCharSource( Charsets.UTF_8 ).read() == 'hello';

        when:
        resource.setLastModified( 0 );

        then:
        resource.getLastModified() == 0
    }
}
