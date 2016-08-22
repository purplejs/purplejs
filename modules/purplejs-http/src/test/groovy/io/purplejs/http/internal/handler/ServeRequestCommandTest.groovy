package io.purplejs.http.internal.handler

import io.purplejs.core.value.ScriptExports
import io.purplejs.http.Status
import io.purplejs.http.mock.MockRequest
import spock.lang.Specification

class ServeRequestCommandTest
    extends Specification
{
    def MockRequest request;

    def ServeRequestCommand command;

    def ScriptExports exports;

    def setup()
    {
        this.request = new MockRequest();
        this.command = new ServeRequestCommand( this.request );
        this.exports = Mock( ScriptExports.class );
    }

    def "methodNotAllowed"()
    {
        when:
        def res = this.command.apply( this.exports );
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
        this.request.method = method;
        this.exports.hasMethod( funcName ) >> true;

        when:
        def res = this.command.apply( this.exports );

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
}
