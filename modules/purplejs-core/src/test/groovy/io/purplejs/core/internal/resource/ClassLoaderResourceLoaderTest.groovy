package io.purplejs.core.internal.resource

import io.purplejs.core.exception.NotFoundException
import io.purplejs.core.resource.ResourcePath

class ClassLoaderResourceLoaderTest
    extends ResourceTestSupport
{
    private ClassLoaderResourceLoader loader;

    private ClassLoaderResourceLoader loaderWithPrefix;

    def setup()
    {
        def classLoader = new URLClassLoader( this.temporaryFolder.root.toURI().toURL() );
        this.loader = new ClassLoaderResourceLoader( classLoader, null );
        this.loaderWithPrefix = new ClassLoaderResourceLoader( classLoader, '/a' );
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

    def "load with prefix"()
    {
        setup:
        writeFile( newFolder( "a" ), 'x.txt', 'hello' );

        when:
        def resource = this.loaderWithPrefix.load( ResourcePath.from( "/x.txt" ) );

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
}
