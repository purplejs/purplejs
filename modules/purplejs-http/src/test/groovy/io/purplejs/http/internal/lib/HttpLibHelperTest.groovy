package io.purplejs.http.internal.lib

import com.google.common.net.MediaType
import io.purplejs.http.mock.MockRequest
import io.purplejs.http.multipart.MultipartForm
import spock.lang.Specification

class HttpLibHelperTest
    extends Specification
{
    def HttpLibHelper helper;

    def MockRequest request;

    def setup()
    {
        this.helper = new HttpLibHelper();
        this.request = new MockRequest();

        this.helper.setRequestProvider( { this.request } );
    }

    def "getRequest"()
    {
        when:
        def actual = this.helper.getRequest();

        then:
        actual == this.request;
    }

    def "isJsonBody"()
    {
        when:
        this.request.setContentType( type != null ? MediaType.parse( type ) : null );

        then:
        this.helper.isJsonBody() == expected;

        where:
        type                               | expected
        null                               | false
        'text/html'                        | false
        'application/json'                 | true
        'application/json; encoding=UTF-8' | true
    }

    def "isMultipart"()
    {
        when:
        this.request.setMultipart( null );

        then:
        !this.helper.isMultipart();

        when:
        this.request.setMultipart( new MultipartForm() );

        then:
        this.helper.isMultipart();
    }

    def "getMultipartForm"()
    {
        when:
        this.request.setMultipart( null );
        def form = this.helper.getMultipartForm();

        then:
        form != null;
    }

    def "getMultipartItem"()
    {
        when:
        this.request.setMultipart( null );
        def item = this.helper.getMultipartItem( 'test', 0 );

        then:
        item == null;
    }
}
