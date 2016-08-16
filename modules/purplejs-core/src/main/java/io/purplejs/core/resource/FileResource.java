package io.purplejs.core.resource;

import java.io.File;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;

final class FileResource
    implements Resource
{
    private final ResourcePath path;

    private final File file;

    FileResource( final ResourcePath path, final File file )
    {
        this.path = path;
        this.file = file;
    }

    @Override
    public ResourcePath getPath()
    {
        return this.path;
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
