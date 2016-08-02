package io.purplejs.resource;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResoucePathTest
{
    @Test
    public void testRoot()
    {
        final ResourcePath path = ResourcePath.from( "/" );
        assertSame( path, ResourcePath.from( "/" ) );
        assertSame( path, ResourcePath.ROOT );
        assertEquals( "/", path.getPath() );
        assertEquals( "/", path.toString() );
        assertEquals( "", path.getName() );
        assertEquals( "", path.getExtension() );
        assertEquals( "", path.getNameWithoutExtension() );
        assertEquals( true, path.isRoot() );
        assertNull( path.getParent() );
    }

    @Test
    public void testNonRoot()
    {
        final ResourcePath path = ResourcePath.from( "/a/b/c.txt" );
        assertEquals( "/a/b/c.txt", path.getPath() );
        assertEquals( "/a/b/c.txt", path.toString() );
        assertEquals( "c.txt", path.getName() );
        assertEquals( "txt", path.getExtension() );
        assertEquals( "c", path.getNameWithoutExtension() );
        assertEquals( false, path.isRoot() );

        final ResourcePath parent1 = path.getParent();
        assertNotNull( parent1 );
        assertEquals( "/a/b", parent1.getPath() );

        final ResourcePath parent2 = parent1.getParent();
        assertNotNull( parent2 );
        assertEquals( "/a", parent2.getPath() );

        final ResourcePath parent3 = parent2.getParent();
        assertNotNull( parent3 );
        assertEquals( "/", parent3.getPath() );
        assertEquals( true, parent3.isRoot() );
    }

    @Test
    public void testEquals()
    {
        testEquals( "/", "/", true );
        testEquals( "", "/", true );
        testEquals( "/a/b", "a/b/", true );
        testEquals( "/a", "/a/b", false );
        testEquals( "/a/b", "/a/b/c", false );
    }

    @Test
    public void testEquals_non_compliant()
    {
        assertFalse( ResourcePath.from( "/" ).equals( "other" ) );
    }

    private void testEquals( final String path1, final String path2, final boolean flag )
    {
        final boolean result = ResourcePath.from( path1 ).equals( ResourcePath.from( path2 ) );
        assertEquals( flag, result );
    }

    @Test
    public void testResolve()
    {
        testResolve( "/", "", "/" );
        testResolve( "/", ".", "/" );
        testResolve( "/a", "/b/c", "/b/c" );
        testResolve( "/a", "b/c", "/a/b/c" );
        testResolve( "/a/b", "../c", "/a/c" );
    }

    private void testResolve( final String uri, final String path, final String resolved )
    {
        final ResourcePath path1 = ResourcePath.from( uri );
        final ResourcePath path2 = path1.resolve( path );

        assertNotNull( path2 );
        assertEquals( resolved, path2.toString() );
    }

    @Test
    public void testHashCode()
    {
        final ResourcePath path1 = ResourcePath.from( "/a/b" );
        final ResourcePath path2 = ResourcePath.from( "/a/b" );
        final ResourcePath path3 = ResourcePath.from( "/a" );

        assertEquals( path1.hashCode(), path2.hashCode() );
        assertNotEquals( path1.hashCode(), path3.hashCode() );
    }
}
