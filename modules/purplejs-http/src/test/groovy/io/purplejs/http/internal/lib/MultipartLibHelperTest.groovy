package io.purplejs.http.internal.lib

import io.purplejs.http.multipart.MultipartForm
import spock.lang.Specification

class MultipartLibHelperTest
    extends Specification
{
    def "getFormAsJson"()
    {
        when:
        def helper = new MultipartLibHelper( new MultipartForm() );
        def json = helper.getFormAsJson();

        then:
        json != null;
    }

    def "getItemAsJson"()
    {
        when:
        def helper = new MultipartLibHelper( new MultipartForm() );
        def json = helper.getItemAsJson( 'test', 0 );

        then:
        json == null;
    }
}
