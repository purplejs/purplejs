package org.purplejs.impl.resource;

import java.net.URL;

import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

final class ClassLoaderResourceLoader
    implements ResourceLoader
{
    private final ClassLoader loader;

    private final String basePath;

    public ClassLoaderResourceLoader( final ClassLoader loader, final String basePath )
    {
        this.loader = loader;
        this.basePath = normalizePath( basePath );
    }

    @Override
    public Resource load( final ResourcePath path )
    {
        final String location = resolveLocation( path );

        final URL url = this.loader.getResource( location );
        if ( url != null )
        {
            return new UrlResource( path, url );
        }

        throw new ResourceNotFoundException( path );
    }

    private String normalizePath( final String path )
    {
        if ( path == null )
        {
            return null;
        }

        final Iterable<String> parts = Splitter.on( '/' ).omitEmptyStrings().split( path );
        return Strings.emptyToNull( Joiner.on( '/' ).join( parts ) );
    }

    private String resolveLocation( final ResourcePath path )
    {
        if ( this.basePath == null )
        {
            return path.getPath().substring( 1 );
        }

        return normalizePath( this.basePath + "/" + path.getPath() );
    }
}
