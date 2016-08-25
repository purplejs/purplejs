package io.purplejs.router.internal

import jdk.nashorn.api.scripting.JSObject
import spock.lang.Specification

class RouteMatchImplTest
    extends Specification
{
    def "test methods"()
    {
        setup:
        def request = Mock( JSObject );
        def handler = Mock( JSObject );

        when:
        def params = ['a': '1', 'b': '2'];
        def match = new RouteMatchImpl( params, handler );

        then:
        match.handler == handler;

        when:
        match.appendPathParams( request );

        then:
        1 * request.setMember( 'a', '1' );
        1 * request.setMember( 'b', '2' );
    }
}
