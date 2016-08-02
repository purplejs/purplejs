package io.purplejs.impl.resource;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;

import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;

final class UrlResource
    implements Resource
{
    private final ResourcePath path;

    private final URL url;

    UrlResource( final ResourcePath path, final URL url )
    {
        this.path = path;
        this.url = url;
    }

    @Override
    public ResourcePath getPath()
    {
        return this.path;
    }

    @Override
    public long getSize()
    {
        final URLConnection conn = openConnection();
        return conn != null ? conn.getContentLength() : -1;
    }

    @Override
    public long getLastModified()
    {
        final URLConnection conn = openConnection();
        return conn != null ? conn.getLastModified() : -1;
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
            return null;
        }
    }
}
