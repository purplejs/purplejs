package io.purplejs.boot.internal.request;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

import com.google.common.base.Optional;
import com.google.common.io.ByteSource;

final class PartByteSource
    extends ByteSource
{
    private final Part part;

    PartByteSource( final Part part )
    {
        this.part = part;
    }

    @Override
    public InputStream openStream()
        throws IOException
    {
        return this.part.getInputStream();
    }

    @Override
    public Optional<Long> sizeIfKnown()
    {
        return Optional.of( this.part.getSize() );
    }
}
