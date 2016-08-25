package io.purplejs.router.internal

import jdk.nashorn.api.scripting.JSObject
import spock.lang.Specification

class RouteTest
    extends Specification
{
    def "test simple"()
    {
        setup:
        def handler = Mock( JSObject );

        when:
        def route = new Route( 'GET', RoutePattern.compile( '/a/{b}' ), handler );

        then:
        route.handler == handler;
        route.matches( 'GET', '/a/1' );
        route.getPathParams( '/a/1' ) == ['b': '1'];
    }

    def "test method matching"()
    {
        expect:
        def route = new Route( method, RoutePattern.compile( '/a/b' ), null );
        route.matches( actualMethod, '/a/b' ) == matches;

        where:
        method | actualMethod | matches
        'GET'  | 'GET'        | true
        'GET'  | 'POST'       | false
        '*'    | 'POST'       | true
    }

    def "test path matching"()
    {
        expect:
        def route = new Route( 'GET', RoutePattern.compile( pattern ), null );
        route.matches( 'GET', path ) == matches;

        where:
        pattern | path   | matches
        '/a/b'  | '/a/b' | true
        '/a/b'  | '/a'   | false
    }
}
