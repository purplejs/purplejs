package io.purplejs.core.resource

import spock.lang.Specification

class ResourcePathTest
    extends Specification
{
    def "root"()
    {
        when:
        def path = ResourcePath.from( "/" );

        then:
        path == ResourcePath.from( "/" );
        path == ResourcePath.ROOT;
        path.path == '/';
        path.toString() == '/';
        path.name == '';
        path.extension == '';
        path.nameWithoutExtension == '';
        path.isRoot();
        path.parent == null;
    }

    def "non root"()
    {
        when:
        def path = ResourcePath.from( "/a/b/c.txt" );

        then:
        path.path == '/a/b/c.txt';
        path.toString() == '/a/b/c.txt';
        path.name == 'c.txt';
        path.extension == 'txt';
        path.nameWithoutExtension == 'c';
        !path.isRoot();
        path.parent != null;
        path.parent.path == '/a/b';
    }

    def "equals"()
    {
        expect:
        ResourcePath.from( path1 ) == ResourcePath.from( path2 ) == result;

        where:
        path1  | path2    | result
        '/'    | '/'      | true
        ''     | '/'      | true
        '/a/b' | 'a/b/'   | true
        '/a'   | '/a/b'   | false
        '/a/b' | '/a/b/c' | false
    }

    def "equals not right type"()
    {
        when:
        def path = ResourcePath.from( '/a/b' );

        then:
        path != 'test';
    }

    def "test hashCode"()
    {
        when:
        def path1 = ResourcePath.from( "/a/b" );
        def path2 = ResourcePath.from( "/a/b" );
        def path3 = ResourcePath.from( "/a" );

        then:
        path1.hashCode() == path2.hashCode();
        path1.hashCode() != path3.hashCode();
    }

    def "resolve"()
    {
        setup:
        def path1 = ResourcePath.from( uri );
        def path2 = path1.resolve( path );

        expect:
        path2 != null;
        path2.path == resolved;

        where:
        uri    | path   | resolved
        '/'    | ''     | '/'
        '/'    | '.'    | '/'
        '/a'   | '/b/c' | '/b/c'
        '/a'   | 'b/c'  | '/a/b/c'
        '/a/b' | '../c' | '/a/c'
    }
}
