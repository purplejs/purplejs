package io.purplejs.http.mock

import com.google.common.io.ByteSource
import com.google.common.net.MediaType
import io.purplejs.http.MultipartForm
import spock.lang.Specification

class MockRequestTest
    extends Specification
{
    def "test accessors"()
    {
        setup:
        def body = ByteSource.wrap( 'hello'.bytes );
        def form = Mock( MultipartForm.class );
        def raw = new Object();

        when:
        def req = new MockRequest();
        req.setMethod( 'POST' );
        req.setUri( 'http://localhost:8080' );
        req.setContentLength( 10 );
        req.setContentType( MediaType.ANY_TEXT_TYPE );
        req.setBody( body );
        req.setMultipart( form );
        req.setWebSocket( true );
        req.setRaw( raw );

        then:
        req.getMethod() == 'POST';
        req.getUri().toString() == 'http://localhost:8080';
        req.getContentLength() == 10;
        req.getContentType() == MediaType.ANY_TEXT_TYPE;
        req.getBody() == body;
        req.getMultipart() == form;
        req.isWebSocket();
        req.getRaw() == raw;
    }

    def "setUri, illegal uri"()
    {
        when:
        def req = new MockRequest();
        req.setUri( ':illegal' );

        then:
        thrown URISyntaxException;
    }
}
