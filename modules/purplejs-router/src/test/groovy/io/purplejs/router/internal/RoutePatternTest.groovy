package io.purplejs.router.internal

import spock.lang.Specification

class RoutePatternTest
    extends Specification
{
    def "simple patterns"()
    {
        expect:
        def compiled = RoutePattern.compile( pattern );
        compiled.matches( path ) == matches;

        where:
        pattern    | path         | matches
        '/one/two' | '/one'       | false
        '/one/two' | '/one/two'   | true
        '/one/two' | '/one/two/3' | false
        '/one/two' | '/one/tw'    | false
    }

    def "simple path params"()
    {
        expect:
        def compiled = RoutePattern.compile( pattern );
        compiled.matches( path ) == matches;
        compiled.getPathParams( path ) == params;

        where:
        pattern      | path       | matches | params
        '/one/{num}' | '/one'     | false   | [:]
        '/one/{num}' | '/one/two' | true    | ['num': 'two']
        '/one/{num}' | '/one'     | false   | [:]
        '/{a}/{b}'   | '/one/two' | true    | ['a': 'one', 'b': 'two']
    }

    def "regexp path params"()
    {
        expect:
        def compiled = RoutePattern.compile( pattern );
        compiled.matches( path ) == matches;
        compiled.getPathParams( path ) == params;

        where:
        pattern             | path            | matches | params
        '/one/{num:[0-9]+}' | '/one'          | false   | [:]
        '/one/{num:[0-9]+}' | '/one/two'      | false   | [:]
        '/one/{num:[0-9]+}' | '/one/11'       | true    | ['num': '11']
        '/{first:.+}/last'  | '/one'          | false   | [:]
        '/{first:.+}/last'  | '/one/last'     | true    | ['first': 'one']
        '/{first:.+}/last'  | '/one/two/last' | true    | ['first': 'one/two']
    }
}
