package io.purplejs.core.internal.resolver;

import org.junit.Test;

import io.purplejs.core.resource.ResourcePath;

import static org.junit.Assert.*;

public class StandardPathResolverTest
{
    private ResourcePath resolve( final String dir, final String path )
    {
        return new StandardPathResolver( ResourcePath.from( dir ) ).resolve( path );
    }

    @Test
    public void resolve()
    {
        final ResourcePath resolved1 = resolve( "/a/b", "test.txt" );
        assertEquals( "/a/b/test.txt", resolved1.toString() );

        final ResourcePath resolved2 = resolve( "/a/b", "./test.txt" );
        assertEquals( "/a/b/test.txt", resolved2.toString() );

        final ResourcePath resolved3 = resolve( "/a/b", "../test.txt" );
        assertEquals( "/a/test.txt", resolved3.toString() );

        final ResourcePath resolved4 = resolve( "/a/b", "/test.txt" );
        assertEquals( "/test.txt", resolved4.toString() );
    }
}
