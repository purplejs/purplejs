package io.purplejs.http

import spock.lang.Specification

class CookieTest
    extends Specification
{
    def "default values"()
    {
        when:
        def cookie = new Cookie( "test" );

        then:
        cookie.name == "test";
        cookie.value == null;
        cookie.domain == null;
        cookie.path == null;
        cookie.comment == null;
        !cookie.isSecure()
        !cookie.isHttpOnly();
        cookie.maxAge == -1;
    }

    def "getters and setters"()
    {
        when:
        def cookie = new Cookie( "test" );
        cookie.value = "value";
        cookie.domain = "domain";
        cookie.path = "path";
        cookie.comment = "comment";
        cookie.secure = true;
        cookie.httpOnly = true;
        cookie.maxAge = 3600;

        then:
        cookie.name == "test";
        cookie.value == "value";
        cookie.domain == "domain";
        cookie.path == "path";
        cookie.comment == "comment";
        cookie.isSecure()
        cookie.isHttpOnly();
        cookie.maxAge == 3600;
    }

    def "create null name"()
    {
        when:
        new Cookie( null );

        then:
        thrown IllegalArgumentException;
    }

    def "create empty name"()
    {
        when:
        new Cookie( "" );

        then:
        thrown IllegalArgumentException;
    }
}
