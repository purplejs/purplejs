package io.purplejs.core.resource

import spock.lang.Specification

class ResourceResultTest
    extends Specification
{
    def "found"()
    {
        setup:
        def result = new ResourceResult()
        def path = ResourcePath.from( "/a/b" )

        when:
        result.found = null

        then:
        result.found == null
        result.get() == null

        when:
        result.found = path

        then:
        result.found == path
        result.get() == path
    }

    def "scanned"()
    {
        setup:
        def result = new ResourceResult()
        def path1 = ResourcePath.from( "/a/b" )
        def path2 = ResourcePath.from( "/a/c" )

        when:
        result.addScanned( path1 )
        result.addScanned( path2 )

        then:
        result.scanned == [path1, path2]
    }
}
