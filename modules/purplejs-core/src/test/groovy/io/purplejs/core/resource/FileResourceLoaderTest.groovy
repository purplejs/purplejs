package io.purplejs.core.resource

import io.purplejs.core.exception.NotFoundException

class FileResourceLoaderTest
    extends ResourceTestSupport
{
    private FileResourceLoader loader;

    def setup()
    {
        this.loader = new FileResourceLoader( this.temporaryFolder.root );
    }

    def "load"()
    {
        setup:
        writeFile( newFolder( "a" ), 'x.txt', 'hello' );

        when:
        def resource = this.loader.load( ResourcePath.from( "/a/x.txt" ) );

        then:
        resource != null;
    }

    def "file not found"()
    {
        when:
        this.loader.load( ResourcePath.from( "/a/x.txt" ) );

        then:
        thrown NotFoundException;
    }

    def "load directory"()
    {
        setup:
        newFolder( "a" );

        when:
        this.loader.load( ResourcePath.from( "/a" ) );

        then:
        thrown NotFoundException;
    }
}
