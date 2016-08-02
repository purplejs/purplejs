package io.purplejs.impl.resolver;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class RequirePathResolverTest
{
    private ResourceLoader loader;

    @Before
    public void setUp()
    {
        this.loader = Mockito.mock( ResourceLoader.class );
    }

    private ResourcePath resolve( final String dir, final String path )
    {
        return new RequirePathResolver( this.loader, path( dir ) ).resolve( path );
    }

    private ResourcePath path( final String path )
    {
        return ResourcePath.from( path );
    }

    private void mockExists( final String path )
    {
        Mockito.when( this.loader.exists( path( path ) ) ).thenReturn( true );
    }

    @Test
    public void resolve_file()
    {
        mockExists( "/c.txt" );
        mockExists( "/a/b/c.txt" );
        mockExists( "/a/b/c.js" );
        mockExists( "/a/b/c.json" );

        final ResourcePath path1 = resolve( "/a/b", "/c.txt" );
        assertEquals( "/c.txt", path1.toString() );

        final ResourcePath path2 = resolve( "/a/b", "./c.txt" );
        assertEquals( "/a/b/c.txt", path2.toString() );

        final ResourcePath path3 = resolve( "/a/b/d", "../c.txt" );
        assertEquals( "/a/b/c.txt", path3.toString() );

        final ResourcePath path4 = resolve( "/a/b", "./d.txt" );
        assertNull( path4 );
    }

    @Test
    public void resolve_file_js()
    {
        mockExists( "/a/b/c.js" );
        mockExists( "/a/b/c.json" );

        final ResourcePath path = resolve( "/a/b", "./c" );
        assertEquals( "/a/b/c.js", path.toString() );
    }

    @Test
    public void resolve_file_json()
    {
        mockExists( "/a/b/c.json" );

        final ResourcePath path = resolve( "/a/b", "./c" );
        assertEquals( "/a/b/c.json", path.toString() );
    }

    @Test
    public void resolve_dir_js()
    {
        mockExists( "/a/b/c/index.js" );
        mockExists( "/a/b/c/index.json" );

        final ResourcePath path = resolve( "/a/b", "./c" );
        assertEquals( "/a/b/c/index.js", path.toString() );
    }

    @Test
    public void resolve_dir_json()
    {
        mockExists( "/a/b/c/index.json" );

        final ResourcePath path = resolve( "/a/b", "./c" );
        assertEquals( "/a/b/c/index.json", path.toString() );
    }

    @Test
    public void resolve_search_lib()
    {
        mockExists( "/lib/a/b" );
        mockExists( "/lib/a/c.js" );
        mockExists( "/lib/a/d/index.json" );

        final ResourcePath path1 = resolve( "/a/b", "a/b" );
        assertEquals( "/lib/a/b", path1.toString() );

        final ResourcePath path2 = resolve( "/a/b", "a/c" );
        assertEquals( "/lib/a/c.js", path2.toString() );

        final ResourcePath path3 = resolve( "/a/b", "a/d" );
        assertEquals( "/lib/a/d/index.json", path3.toString() );

        final ResourcePath path4 = resolve( "/a/b", "x" );
        assertNull( path4 );
    }

    @Test
    public void resolve_search_node_modules()
    {
        mockExists( "/a/b/node_modules/a/b.js" );
        mockExists( "/a/node_modules/d.json" );
        mockExists( "/node_modules/a/index.js" );

        final ResourcePath path1 = resolve( "/a/b", "a/b" );
        assertEquals( "/a/b/node_modules/a/b.js", path1.toString() );

        final ResourcePath path2 = resolve( "/a/b", "d" );
        assertEquals( "/a/node_modules/d.json", path2.toString() );

        final ResourcePath path3 = resolve( "/a/b", "a" );
        assertEquals( "/node_modules/a/index.js", path3.toString() );

        final ResourcePath path4 = resolve( "/a/b", "x" );
        assertNull( path4 );
    }

    @Test
    public void findSearchPaths()
    {
        final List<ResourcePath> paths1 = RequirePathResolver.findSearchPaths( path( "/a/b/c" ) );
        assertEquals( "[/lib, /a/b/c/node_modules, /a/b/node_modules, /a/node_modules, /node_modules]", paths1.toString() );

        final List<ResourcePath> paths2 = RequirePathResolver.findSearchPaths( path( "/a/b/node_modules" ) );
        assertEquals( "[/lib, /a/b/node_modules, /a/node_modules, /node_modules]", paths2.toString() );

        final List<ResourcePath> paths3 = RequirePathResolver.findSearchPaths( path( "/" ) );
        assertEquals( "[/lib, /node_modules]", paths3.toString() );
    }
}
