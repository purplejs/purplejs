package io.purplejs.router.internal

import jdk.nashorn.api.scripting.JSObject
import spock.lang.Specification

class RouterTest
    extends Specification
{
    def Router router;

    def setup()
    {
        this.router = new Router();
    }

    def "test matches"()
    {
        setup:
        def handler1 = Mock( JSObject.class );
        def handler2 = Mock( JSObject.class );
        def handler3 = Mock( JSObject.class );

        this.router.add( 'GET', '/a/1', handler1 );
        this.router.add( 'HEAD', '/a/2', handler2 );
        this.router.add( '*', '/a/1', handler3 );

        when:
        def match = this.router.matches( 'GET', '/a' );

        then:
        match == null;

        when:
        match = this.router.matches( 'GET', '/a/1' );

        then:
        match != null;
        match.handler == handler1;

        when:
        match = this.router.matches( 'DELETE', '/a/1' );

        then:
        match != null;
        match.handler == handler3;
    }

    def "test match head case"()
    {
        setup:
        def handler = Mock( JSObject.class );
        this.router.add( 'GET', '/a', handler );

        when:
        def match = this.router.matches( 'HEAD', '/a/b' );

        then:
        match == null;

        when:
        match = this.router.matches( 'HEAD', '/a' );

        then:
        match != null;
        match.handler == handler;
    }
}
