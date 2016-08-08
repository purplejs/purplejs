package io.purplejs.servlet;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockServletConfig;

import io.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class ScriptServletConfigTest
{
    private MockServletConfig servletConfig;

    @Before
    public void setUp()
    {
        this.servletConfig = new MockServletConfig();
    }

    private ScriptServletConfig newConfig()
    {
        return new ScriptServletConfig( this.servletConfig );
    }

    @Test
    public void getResource()
    {
        this.servletConfig.addInitParameter( "resource", "/a/b.js" );

        final ResourcePath path = newConfig().getResource();
        assertEquals( ResourcePath.from( "/a/b.js" ), path );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResource_notSet()
    {
        newConfig().getResource();
    }

    @Test
    public void getConfig()
    {
        this.servletConfig.addInitParameter( "something", "a" );
        this.servletConfig.addInitParameter( "config.a", "1" );
        this.servletConfig.addInitParameter( "config.b", "2" );

        final Map<String, String> map = newConfig().getConfig();
        assertNotNull( map );
        assertEquals( "{a=1, b=2}", map.toString() );
    }

    @Test
    public void getConfig_notSet()
    {
        final Map<String, String> map = newConfig().getConfig();
        assertNotNull( map );
        assertEquals( "{}", map.toString() );
    }

    @Test
    public void getDevSourceDirs()
    {
        this.servletConfig.addInitParameter( "devSourceDirs", "/a,/b" );

        final List<File> list = newConfig().getDevSourceDirs();
        assertNotNull( list );
        assertEquals( 2, list.size() );
        assertEquals( "/a", list.get( 0 ).getPath() );
        assertEquals( "/b", list.get( 1 ).getPath() );
    }

    @Test
    public void getDevSourceDirs_notSet()
    {
        final List<File> list = newConfig().getDevSourceDirs();
        assertNotNull( list );
        assertEquals( 0, list.size() );
    }
}
