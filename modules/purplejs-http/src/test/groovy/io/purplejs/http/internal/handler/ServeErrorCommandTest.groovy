package io.purplejs.http.internal.handler

import io.purplejs.core.value.ScriptExports
import io.purplejs.http.RequestBuilder
import io.purplejs.http.Status
import spock.lang.Specification

class ServeErrorCommandTest
    extends Specification
{
    def RequestBuilder requestBuilder;

    def Status status;

    def Throwable cause;

    def ScriptExports exports;

    def setup()
    {
        this.requestBuilder = RequestBuilder.newBuilder();
        this.status = Status.OK;
        this.cause = null;
        this.exports = Mock( ScriptExports.class );
    }

    private ServeErrorCommand newCommand()
    {
        return new ServeErrorCommand( this.requestBuilder.build(), this.status, this.cause );
    }

    def "no method"()
    {
        when:
        def res = newCommand().apply( this.exports );

        then:
        res == null;
    }

    def "handleError"()
    {
        setup:
        this.exports.hasMethod( 'handleError' ) >> true;

        when:
        def res = newCommand().apply( this.exports );

        then:
        res != null;
    }

    def "handle404"()
    {
        setup:
        this.status = Status.NOT_FOUND;
        this.exports.hasMethod( 'handle404' ) >> true;

        when:
        def res = newCommand().apply( this.exports );

        then:
        res != null;
    }

    def "handle404, not right status"()
    {
        setup:
        this.exports.hasMethod( 'handle404' ) >> true;

        when:
        def res = newCommand().apply( this.exports );

        then:
        res == null;
    }
}
