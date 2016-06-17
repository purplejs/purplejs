package io.purplejs.impl.resource;

import java.io.File;
import java.net.URL;

import io.purplejs.resource.ResourcePath;

import com.google.common.base.Throwables;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;

final class FileResource
    extends AbstractResource
{
    private final File file;

    public FileResource( final ResourcePath path, final File file )
    {
        super( path );
        this.file = file;
    }

    @Override
    public URL getUrl()
    {
        try
        {
            return this.file.toURI().toURL();
        }
        catch ( final Exception e )
        {
            throw Throwables.propagate( e );
        }
    }

    @Override
    public long getSize()
    {
        return this.file.length();
    }

    @Override
    public long getLastModified()
    {
        return this.file.lastModified();
    }

    @Override
    public ByteSource getBytes()
    {
        return Files.asByteSource( this.file );
    }
}
