package io.purplejs.boot.internal.servlet

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.springframework.mock.web.MockFilterConfig
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import javax.servlet.FilterChain

class AssetFilterTest
    extends Specification
{
    @Rule
    TemporaryFolder temporaryFolder = new TemporaryFolder();

    def AssetFilter filter;

    def MockHttpServletRequest request;

    def MockHttpServletResponse response;

    def setup()
    {
        this.filter = new AssetFilter();
        this.filter.init( new MockFilterConfig() );

        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();
    }

    def cleanup()
    {
        this.filter.destroy();
    }

    def "not found"()
    {
        setup:
        def chain = Mock( FilterChain.class );

        when:
        this.filter.doFilter( this.request, this.response, chain );

        then:
        1 * chain.doFilter( this.request, this.response );
    }

    def "asset found"()
    {
        setup:
        def chain = Mock( FilterChain.class );
        this.request.setPathInfo( '/test.txt' );

        when:
        this.filter.doFilter( this.request, this.response, chain );

        then:
        0 * chain.doFilter( this.request, this.response );
        this.response.getContentType() == 'text/plain';
        this.response.getContentAsString().trim() == 'Hello World!';
    }

    def "asset found - dev mode"()
    {
        setup:
        this.filter.devMode = true;
        this.filter.devSourceDirs = [this.temporaryFolder.root];

        def assetsFolder = this.temporaryFolder.newFolder( 'assets' );
        new File( assetsFolder, 'test.txt' ) << 'from file';

        def chain = Mock( FilterChain.class );
        this.request.setPathInfo( '/test.txt' );

        when:
        this.filter.doFilter( this.request, this.response, chain );

        then:
        0 * chain.doFilter( this.request, this.response );
        this.response.getContentType() == 'text/plain';
        this.response.getContentAsString().trim() == 'from file';
    }

    def "not found - dev mode"()
    {
        setup:
        this.filter.devMode = true;
        this.filter.devSourceDirs = [this.temporaryFolder.root];
        def chain = Mock( FilterChain.class );

        when:
        this.filter.doFilter( this.request, this.response, chain );

        then:
        1 * chain.doFilter( this.request, this.response );
    }

    def "asset found - mimeType unknown"()
    {
        setup:
        def chain = Mock( FilterChain.class );
        this.request.setPathInfo( '/test.abc' );

        when:
        this.filter.doFilter( this.request, this.response, chain );

        then:
        0 * chain.doFilter( this.request, this.response );
        this.response.getContentType() == 'application/octet-stream';
        this.response.getContentAsString().trim() == 'Hello World!';
    }
}
