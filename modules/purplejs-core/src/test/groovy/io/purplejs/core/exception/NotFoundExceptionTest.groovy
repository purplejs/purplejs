package io.purplejs.core.exception

import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class NotFoundExceptionTest
    extends Specification
{
    def "with path"()
    {
        when:
        def path = ResourcePath.from( "a.txt" )
        def ex = new NotFoundException( path )

        then:
        ex.message == "Resource [/a.txt] not found"
    }

    def "with message"()
    {
        when:
        def ex = new NotFoundException( "message" )

        then:
        ex.message == "message"
    }

    def "with scanned"()
    {
        when:
        def ex = new NotFoundException( "message" )
        def scanned = [ResourcePath.from( "/a" )]
        ex.scanned = scanned

        then:
        ex.message == "message"
        ex.scanned == scanned
    }
}
