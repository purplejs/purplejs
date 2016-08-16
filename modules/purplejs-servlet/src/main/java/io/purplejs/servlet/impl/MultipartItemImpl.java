package io.purplejs.servlet.impl;

import javax.servlet.http.Part;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.MultipartItem;
import io.purplejs.core.exception.ExceptionHelper;

final class MultipartItemImpl
    implements MultipartItem
{
    private final Part part;

    private final ByteSource bytes;

    MultipartItemImpl( final Part part )
    {
        this.part = part;
        this.bytes = new PartByteSource( this.part );
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
        return this.bytes;
    }

    @Override
    public String getAsString()
    {
        try
        {
            return this.bytes.asCharSource( Charsets.UTF_8 ).read();
        }
        catch ( final Exception e )
        {
            throw ExceptionHelper.unchecked( e );
        }
    }

    @Override
    public long getSize()
    {
        return this.part.getSize();
    }

    void delete()
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
