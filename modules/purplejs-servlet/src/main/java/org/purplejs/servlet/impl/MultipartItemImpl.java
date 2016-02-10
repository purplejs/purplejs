package org.purplejs.servlet.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

import org.purplejs.http.MultipartItem;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

final class MultipartItemImpl
    extends ByteSource
    implements MultipartItem
{
    private final Part part;

    public MultipartItemImpl( final Part part )
    {
        this.part = part;
    }

    @Override
    public String getName()
    {
        return this.part.getName();
    }

    @Override
    public String getFileName()
    {
        return this.part.getSubmittedFileName();
    }

    @Override
    public MediaType getContentType()
    {
        final String itemContentType = this.part.getContentType();
        return itemContentType != null ? MediaType.parse( itemContentType ) : null;
    }

    @Override
    public ByteSource getBytes()
    {
        return this;
    }

    @Override
    public String getAsString()
    {
        try
        {
            return asCharSource( Charsets.UTF_8 ).read();
        }
        catch ( final Exception e )
        {
            throw Throwables.propagate( e );
        }
    }

    @Override
    public long getSize()
    {
        return this.part.getSize();
    }

    @Override
    public long size()
    {
        return getSize();
    }

    @Override
    public boolean isEmpty()
    {
        return getSize() == 0;
    }

    @Override
    public InputStream openStream()
        throws IOException
    {
        return this.part.getInputStream();
    }

    public void delete()
    {
        try
        {
            this.part.delete();
        }
        catch ( final Exception e )
        {
            // Do nothing
        }
    }
}
