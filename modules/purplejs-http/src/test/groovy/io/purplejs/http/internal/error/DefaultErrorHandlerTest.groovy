package io.purplejs.http.internal.error

import com.google.common.collect.Lists
import com.google.common.net.HttpHeaders
import com.google.common.net.MediaType
import io.purplejs.core.exception.ProblemException
import io.purplejs.core.resource.ResourcePath
import io.purplejs.http.RequestBuilder
import io.purplejs.http.Status
import spock.lang.Specification

class DefaultErrorHandlerTest
    extends Specification
{
    def DefaultErrorHandler handler;

    def ErrorInfoImpl info;

    def setup()
    {
        this.handler = new DefaultErrorHandler();
        this.info = new ErrorInfoImpl();
    }

    def "error page"()
    {
        setup:
        this.info.status = Status.NOT_FOUND;
        this.info.request = RequestBuilder.newBuilder().
            header( HttpHeaders.ACCEPT, accept ).
            build();

        when:
        def res = this.handler.handle( this.info );

        then:
        res != null;
        res.status == Status.NOT_FOUND;
        res.contentType == type;
        res.body.size() > 0;

        where:
        accept                       | type
        ''                           | MediaType.JSON_UTF_8
        'text/html'                  | MediaType.HTML_UTF_8
        'text/html,application/json' | MediaType.HTML_UTF_8
        'application/json'           | MediaType.JSON_UTF_8
        'application/json,text/html' | MediaType.JSON_UTF_8
        'unknown/other'              | MediaType.JSON_UTF_8
    }

    def "error with cause"()
    {
        setup:
        this.info.status = Status.NOT_FOUND;
        this.info.request = RequestBuilder.newBuilder().
            header( HttpHeaders.ACCEPT, 'text/html' ).
            build();
        this.info.cause = new IOException( 'Some error' );

        when:
        def res = this.handler.handle( this.info );

        then:
        res != null;
        res.status == Status.NOT_FOUND;
        res.contentType == MediaType.HTML_UTF_8;
        res.body.size() > 0;
    }

    def "error with problem"()
    {
        setup:
        this.info.request = RequestBuilder.newBuilder().
            header( HttpHeaders.ACCEPT, 'text/html' ).
            build();
        this.info.status = Status.NOT_FOUND;
        this.info.path = ResourcePath.from( '/test.js' );
        this.info.lines = Lists.newArrayList( 'line1', 'line2' );
        this.info.cause = ProblemException.newBuilder().
            lineNumber( 1 ).
            callLine( 'test1', 1 ).
            callLine( 'test2', 2 ).
            path( ResourcePath.from( '/test.js' ) ).
            build();

        when:
        def res = this.handler.handle( this.info );

        then:
        res != null;
        res.status == Status.NOT_FOUND;
        res.contentType == MediaType.HTML_UTF_8;
        res.body.size() > 0;
    }

    def "error with problem, no path or call-stack"()
    {
        setup:
        this.info.request = RequestBuilder.newBuilder().
            header( HttpHeaders.ACCEPT, 'text/html' ).
            build();
        this.info.status = Status.NOT_FOUND;
        this.info.cause = ProblemException.newBuilder().
            build();

        when:
        def res = this.handler.handle( this.info );

        then:
        res != null;
        res.status == Status.NOT_FOUND;
        res.contentType == MediaType.HTML_UTF_8;
        res.body.size() > 0;
    }
}
