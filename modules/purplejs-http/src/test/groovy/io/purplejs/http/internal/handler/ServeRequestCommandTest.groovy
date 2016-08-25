package io.purplejs.http.internal.handler

import io.purplejs.core.value.ScriptExports
import io.purplejs.http.RequestBuilder
import io.purplejs.http.Status
import spock.lang.Specification

class ServeRequestCommandTest
    extends Specification
{
    def RequestBuilder requestBuilder;

    def ScriptExports exports;

    def setup()
    {
        this.requestBuilder = RequestBuilder.newBuilder();
        this.exports = Mock( ScriptExports.class );
    }

    private ServeRequestCommand newCommand()
    {
        return new ServeRequestCommand( this.requestBuilder.build() );
    }

    def "methodNotAllowed"()
    {
        when:
        def res = newCommand().apply( this.exports );
        this.exports.hasMethod( funcName ) >> true;

        then:
        res != null;
        res.status == Status.METHOD_NOT_ALLOWED;

        where:
        method | funcName
        'GET'  | 'other'
        'HEAD' | 'other'
    }

    def "method order"()
    {
        setup:
        this.requestBuilder.method( method );
        this.exports.hasMethod( funcName ) >> true;

        when:
        def res = newCommand().apply( this.exports );

        then:
        res != null;
        res.status == Status.OK;

        where:
        method | funcName
        'GET'  | 'get'
        'POST' | 'post'
        'HEAD' | 'head'
        'GET'  | 'service'
        'HEAD' | 'get'
    }

    def "throw exception"()
    {
        setup:
        this.requestBuilder.method( 'GET' );
        this.exports.hasMethod( 'get' ) >> true;
        this.exports.executeMethod( _ as String, _ as JsonRequest ) >> { throw new RuntimeException() };

        when:
        newCommand().apply( this.exports );

        then:
        thrown RuntimeException;
    }

    def "no head without get"()
    {
        setup:
        this.requestBuilder.method( 'GET' );

        when:
        def res = newCommand().apply( this.exports );

        then:
        res != null;
        res.status == Status.METHOD_NOT_ALLOWED;
    }
}
