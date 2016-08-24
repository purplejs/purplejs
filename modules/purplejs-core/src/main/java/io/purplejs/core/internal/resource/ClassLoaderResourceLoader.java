package io.purplejs.core.internal.resource;

import java.net.URL;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

final class ClassLoaderResourceLoader
    implements ResourceLoader
{
    private final ClassLoader loader;

    private final String basePath;

    ClassLoaderResourceLoader( final ClassLoader loader, final String basePath )
    {
        this.loader = loader;
        this.basePath = normalizePath( basePath );
    }

    @Override
    public Resource loadOrNull( final ResourcePath path )
    {
        final String location = resolveLocation( path );

        final URL url = this.loader.getResource( location );
        if ( url == null )
        {
            return null;
        }

        return new UrlResource( path, url );
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
