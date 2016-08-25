package io.purplejs.http.internal.lib

import io.purplejs.http.RequestBuilder
import io.purplejs.http.multipart.MultipartForm
import spock.lang.Specification

class HttpLibHelperTest
    extends Specification
{
    def HttpLibHelper helper;

    def RequestBuilder requestBuilder;

    def setup()
    {
        this.helper = new HttpLibHelper();
        this.requestBuilder = RequestBuilder.newBuilder();

        this.helper.setRequestProvider( { requestBuilder.build() } );
    }

    def "getRequest"()
    {
        when:
        def actual = this.helper.getRequest();

        then:
        actual != null;
    }

    def "isJsonBody"()
    {
        when:
        this.requestBuilder.contentType( (String) type );

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
        this.requestBuilder.multipart( null );

        then:
        !this.helper.isMultipart();

        when:
        this.requestBuilder.multipart( new MultipartForm() );

        then:
        this.helper.isMultipart();
    }

    def "getMultipartForm"()
    {
        when:
        this.requestBuilder.multipart( null );
        def form = this.helper.getMultipartForm();

        then:
        form != null;
    }

    def "getMultipartItem"()
    {
        when:
        this.requestBuilder.multipart( null );
        def item = this.helper.getMultipartItem( 'test', 0 );

        then:
        item == null;
    }
}
