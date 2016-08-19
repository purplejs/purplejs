package io.purplejs.http

import com.google.common.net.HttpHeaders
import spock.lang.Specification

class HeadersTest
    extends Specification
{
    def Headers headers;

    def setup()
    {
        this.headers = new Headers();
    }

    def "get/set/remove"()
    {
        when:
        def value = this.headers.get( "key" );

        then:
        value == null;

        when:
        this.headers.set( "key", "value" );
        value = this.headers.get( "key" );

        then:
        value != null;
        value == "value";

        when:
        this.headers.remove( "key" );
        value = this.headers.get( "key" );

        then:
        value == null;
    }

    def "getAccept"()
    {
        when:
        def list = this.headers.getAccept();

        then:
        list.isEmpty();

        when:
        this.headers.set( HttpHeaders.ACCEPT, "text/html,application/json" );
        list = this.headers.getAccept();

        then:
        list.size() == 2;
        list.toString() == '[text/html, application/json]';
    }
}
