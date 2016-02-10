package org.purplejs.servlet.impl;

import javax.servlet.http.HttpServletRequest;

import org.purplejs.http.Attributes;
import org.purplejs.http.Headers;
import org.purplejs.http.MultipartForm;
import org.purplejs.http.Parameters;
import org.purplejs.http.Request;
import org.purplejs.http.impl.AttributesImpl;

import com.google.common.base.Throwables;
import com.google.common.io.ByteSource;

public final class RequestWrapper
    implements Request
{
    private final HttpServletRequest wrapped;

    private final Attributes attributes;

    public RequestWrapper( final HttpServletRequest wrapped )
    {
        this.wrapped = wrapped;
        this.attributes = new AttributesImpl();
    }

    @Override
    public String getMethod()
    {
        return this.wrapped.getMethod();
    }

    @Override
    public Parameters getParameters()
    {
        return null;
    }

    @Override
    public Headers getHeaders()
    {
        return null;
    }

    @Override
    public Attributes getAttributes()
    {
        return this.attributes;
    }

    @Override
    public ByteSource getBody()
    {
        return null;
    }

    @Override
    public MultipartForm getMultipart()
    {
        try
        {
            return new MultipartFormImpl( this.wrapped.getParts() );
        }
        catch ( final Exception e )
        {
            throw Throwables.propagate( e );
        }
    }

    @Override
    public Object getRaw()
    {
        return this.wrapped;
    }
}
