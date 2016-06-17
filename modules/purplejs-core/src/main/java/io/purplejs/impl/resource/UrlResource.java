package io.purplejs.impl.resource;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import io.purplejs.resource.ResourcePath;

import com.google.common.base.Throwables;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;

final class UrlResource
    extends AbstractResource
{
    private final URL url;

    public UrlResource( final ResourcePath path, final URL url )
    {
        super( path );
        this.url = url;
    }

    @Override
    public URL getUrl()
    {
        return this.url;
    }

    @Override
    public long getSize()
    {
        return openConnection().getContentLength();
    }

    @Override
    public long getLastModified()
    {
        return openConnection().getLastModified();
    }

    @Override
    public ByteSource getBytes()
    {
        return Resources.asByteSource( this.url );
    }

    private URLConnection openConnection()
    {
        try
        {
            final URLConnection connection = this.url.openConnection();
            connection.connect();
            return connection;
        }
        catch ( final IOException e )
        {
            throw Throwables.propagate( e );
        }
    }
}
