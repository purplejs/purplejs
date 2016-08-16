package io.purplejs.core.resource;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;

public final class ResourcePath
{
    public final static ResourcePath ROOT = new ResourcePath();

    private final static String SEP = "/";

    private final String path;

    private final String[] elements;

    private ResourcePath( final String... elements )
    {
        this.elements = elements;
        this.path = SEP + Joiner.on( SEP ).join( this.elements );
    }

    public String getPath()
    {
        return this.path;
    }

    public String getName()
    {
        return isRoot() ? "" : this.elements[this.elements.length - 1];
    }

    public String getExtension()
    {
        final String name = getName();
        return Files.getFileExtension( name );
    }

    public String getNameWithoutExtension()
    {
        final String name = getName();
        return Files.getNameWithoutExtension( name );
    }

    public boolean isRoot()
    {
        return this.elements.length == 0;
    }

    public ResourcePath getParent()
    {
        if ( isRoot() )
        {
            return null;
        }

        final String[] result = new String[this.elements.length - 1];
        System.arraycopy( this.elements, 0, result, 0, result.length );
        return from( result );
    }

    public ResourcePath resolve( final String relPath )
    {
        if ( relPath.startsWith( SEP ) )
        {
            return from( relPath );
        }

        return from( this.path + SEP + relPath );
    }

    @Override
    public String toString()
    {
        return this.path;
    }

    @Override
    public boolean equals( final Object o )
    {
        return ( o instanceof ResourcePath ) && ( this.path.equals( ( (ResourcePath) o ).path ) );
    }

    @Override
    public int hashCode()
    {
        return this.path.hashCode();
    }

    private static String normalizePath( final String path )
    {
        return Files.simplifyPath( SEP + path );
    }

    private static String[] split( final String path )
    {
        final Iterable<String> it = Splitter.on( SEP ).omitEmptyStrings().trimResults().split( path );
        return Iterables.toArray( it, String.class );
    }

    private static ResourcePath from( final String[] elements )
    {
        if ( elements.length == 0 )
        {
            return ROOT;
        }

        return new ResourcePath( elements );
    }

    public static ResourcePath from( final String path )
    {
        return from( split( normalizePath( path ) ) );
    }
}
