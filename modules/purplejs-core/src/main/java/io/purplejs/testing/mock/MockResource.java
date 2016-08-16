package io.purplejs.testing.mock;

import com.google.common.io.ByteSource;

import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;

public final class MockResource
    implements Resource
{
    private final ResourcePath path;

    private final byte[] bytes;

    private long lastModified;

    MockResource( final ResourcePath path, final byte[] bytes )
    {
        this.path = path;
        this.bytes = bytes;
        this.lastModified = System.currentTimeMillis();
    }

    @Override
    public ResourcePath getPath()
    {
        return this.path;
    }

    @Override
    public long getSize()
    {
        return this.bytes.length;
    }

    @Override
    public long getLastModified()
    {
        return this.lastModified;
    }

    public void setLastModified( final long lastModified )
    {
        this.lastModified = lastModified;
    }

    @Override
    public ByteSource getBytes()
    {
        return ByteSource.wrap( this.bytes );
    }
}
