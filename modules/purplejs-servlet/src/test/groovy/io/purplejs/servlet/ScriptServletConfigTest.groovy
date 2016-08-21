package io.purplejs.servlet

import org.springframework.mock.web.MockServletConfig
import spock.lang.Specification

class ScriptServletConfigTest
    extends Specification
{
    def MockServletConfig config;

    def setup()
    {
        this.config = new MockServletConfig();
    }

    private ScriptServletConfig newConfig()
    {
        return new ScriptServletConfig( this.config );
    }

    def "getResource"()
    {
        setup:
        this.config.addInitParameter( "resource", "/a/b.js" );

        when:
        def path = newConfig().getResource();

        then:
        path.path == "/a/b.js";
    }

    def "getResource not set"()
    {
        when:
        newConfig().getResource();

        then:
        thrown IllegalArgumentException;
    }

    def "getConfig"()
    {
        setup:
        this.config.addInitParameter( "something", "a" );
        this.config.addInitParameter( "config.a", "1" );
        this.config.addInitParameter( "config.b", "2" );

        when:
        def map = newConfig().getConfig();

        then:
        map != null;
        map.toString() == '[a:1, b:2]';
    }

    def "getConfig not set"()
    {
        when:
        def map = newConfig().getConfig();

        then:
        map != null;
        map.toString() == '[:]';
    }

    def "getDevSourceDirs"()
    {
        setup:
        this.config.addInitParameter( "devSourceDirs", "/a,/b" );

        when:
        def list = newConfig().getDevSourceDirs();

        then:
        list != null;
        list.size() == 2;
        list.toString() == '[/a, /b]';
    }

    def "getDevSourceDirs not set"()
    {
        when:
        def list = newConfig().getDevSourceDirs();

        then:
        list != null;
        list.size() == 0;
    }
}
