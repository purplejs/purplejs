package io.purplejs.core.internal.lib

import com.google.common.base.Charsets
import io.purplejs.core.mock.MockResourceLoader
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

import java.util.function.Consumer

class CoreLibHelperTest
    extends Specification
{
    private CoreLibHelper helper;

    private MockResourceLoader loader;

    def setup()
    {
        this.helper = new CoreLibHelper();
        this.loader = new MockResourceLoader();
    }

    def "newStream"()
    {
        when:
        def stream = this.helper.newStream( 'hello' );

        then:
        stream != null
    }

    def "streamSize"()
    {
        setup:
        def stream = this.helper.newStream( 'hello' );

        when:
        def size = this.helper.streamSize( stream );

        then:
        size == 5L;
    }

    def "streamSize not a stream"()
    {
        when:
        def size = this.helper.streamSize( 'test' );

        then:
        size == 4L;
    }

    def "readText"()
    {
        setup:
        def stream = this.helper.newStream( 'hello' );

        when:
        def text = this.helper.readText( stream );

        then:
        text == 'hello';
    }

    def "readText not a stream"()
    {
        when:
        def text = this.helper.readText( 'test' );

        then:
        text == 'test';
    }

    def "readLines"()
    {
        setup:
        def stream = this.helper.newStream( '1\n2\n3' );

        when:
        def lines = this.helper.readLines( stream );

        then:
        lines.size() == 3;
        lines.toString() == '[1, 2, 3]';
    }

    def "readLines not a stream"()
    {
        when:
        def lines = this.helper.readLines( '1\n2' );

        then:
        lines.size() == 2;
        lines.toString() == '[1, 2]';
    }

    def "processLines"()
    {
        setup:
        def stream = this.helper.newStream( '1\n2\n3' );

        when:
        def result = '';
        this.helper.processLines( stream, new Consumer<String>() {
            @Override
            void accept( final String str )
            {
                result += '-' + str;
            }
        } );

        then:
        result == '-1-2-3';
    }

    def "processLines not a stream"()
    {
        when:
        def result = '';
        this.helper.processLines( '1\n2', new Consumer<String>() {
            @Override
            void accept( final String str )
            {
                result += '-' + str;
            }
        } );

        then:
        result == '-1-2';
    }

    def "loadResource"()
    {
        setup:
        this.loader.addResource( '/a/b.txt', 'hello' );

        when:
        def stream1 = this.helper.loadResource( this.loader, '/a/b.txt' );
        def stream2 = this.helper.loadResource( this.loader, ResourcePath.from( '/a/b.txt' ) );

        then:
        stream1 != null;
        stream1.asCharSource( Charsets.UTF_8 ).read() == 'hello'
        stream2 != null;
        stream2.asCharSource( Charsets.UTF_8 ).read() == 'hello'
    }

    def "loadResource not found"()
    {
        when:
        def stream = this.helper.loadResource( this.loader, ResourcePath.from( '/a/b.txt' ) );

        then:
        stream == null;
    }
}
