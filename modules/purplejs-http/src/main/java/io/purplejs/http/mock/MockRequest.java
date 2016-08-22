package io.purplejs.http.mock;


import java.net.URI;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.core.exception.ExceptionHelper;
import io.purplejs.http.Headers;
import io.purplejs.http.MultipartForm;
import io.purplejs.http.Parameters;
import io.purplejs.http.Request;

public final class MockRequest
    implements Request
{
    private String method = "GET";

    private final Headers headers;

    private final Parameters params;

    private long contentLength = 0L;

    private MediaType contentType;

    private MultipartForm multipartForm;

    private ByteSource body;

    private URI uri;

    private Object raw;

    public MockRequest()
    {
        this.headers = new Headers();
        this.params = new Parameters();
        setUri( "http://localhost" );
        this.body = ByteSource.empty();
    }

    @Override
    public String getMethod()
    {
        return this.method;
    }

    @Override
    public URI getUri()
    {
        return this.uri;
    }

    @Override
    public Parameters getParameters()
    {
        return this.params;
    }

    @Override
    public Headers getHeaders()
    {
        return this.headers;
    }

    @Override
    public MediaType getContentType()
    {
        return this.contentType;
    }

    @Override
    public long getContentLength()
    {
        return this.contentLength;
    }

    @Override
    public ByteSource getBody()
    {
        return this.body;
    }

    @Override
    public MultipartForm getMultipart()
    {
        return this.multipartForm;
    }

    @Override
    public Object getRaw()
    {
        return null;
    }

    public void setMethod( final String method )
    {
        this.method = method;
    }

    public void setBody( final ByteSource body )
    {
        this.body = body;
    }

    public void setContentLength( final long contentLength )
    {
        this.contentLength = contentLength;
    }

    public void setContentType( final MediaType contentType )
    {
        this.contentType = contentType;
    }

    public void setUri( final String uri )
    {
        try
        {
            this.uri = new URI( uri );
        }
        catch ( final Exception e )
        {
            throw ExceptionHelper.unchecked( e );
        }
    }

    public void setMultipartForm( final MultipartForm multipartForm )
    {
        this.multipartForm = multipartForm;
    }
}
