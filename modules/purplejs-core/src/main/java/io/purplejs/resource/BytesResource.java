package io.purplejs.resource;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;

public final class BytesResource
    implements Resource
{
    private final ResourcePath path;

    private final byte[] data;

    private final long lastModified;

    private BytesResource( final ResourcePath path, final byte[] data )
    {
        this.path = path;
        this.data = data;
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
        return this.data.length;
    }

    @Override
    public long getLastModified()
    {
        return this.lastModified;
    }

    @Override
    public ByteSource getBytes()
    {
        return ByteSource.wrap( this.data );
    }

    public static BytesResource create( final ResourcePath path, final String text )
    {
        return create( path, text.getBytes( Charsets.UTF_8 ) );
    }

    public static BytesResource create( final ResourcePath path, final byte[] bytes )
    {
        return new BytesResource( path, bytes );
    }
}
