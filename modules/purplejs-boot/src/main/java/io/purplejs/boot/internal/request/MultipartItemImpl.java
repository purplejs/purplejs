package io.purplejs.boot.internal.request;

import javax.servlet.http.Part;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.core.util.IOHelper;
import io.purplejs.http.multipart.MultipartItem;

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
        return IOHelper.readString( this.bytes );
    }

    @Override
    public long getSize()
    {
        return this.part.getSize();
    }
}
