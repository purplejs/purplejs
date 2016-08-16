package io.purplejs.http.internal.lib;

import java.util.function.Supplier;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.Request;
import io.purplejs.core.registry.Registry;

// TODO: Move this (and http.js) into purplejs-http.
public final class RequestHelper
{
    private Supplier<Request> requestProvider;

    public void init( final Registry registry )
    {
        this.requestProvider = registry.getProvider( Request.class );
    }

    public Request getRequest()
    {
        return this.requestProvider.get();
    }

    public boolean isJsonType( final String type )
    {
        return isJsonType( MediaType.parse( type ) );
    }

    private boolean isJsonType( final MediaType type )
    {
        final String subType = type.subtype();
        return subType.equalsIgnoreCase( "json" ) || subType.toLowerCase().startsWith( "json-" );
    }

    public boolean isTextType( final String type )
    {
        return isTextType( MediaType.parse( type ) );
    }

    private boolean isTextType( final MediaType type )
    {
        return type.withoutParameters().is( MediaType.ANY_TEXT_TYPE ) || isJsonType( type );
    }

    public boolean isJsonBody()
    {
        return isJsonType( getRequest().getContentType() );
    }

    public boolean isTextBody()
    {
        return isTextType( getRequest().getContentType() );
    }

    public boolean isMultipartBody()
    {
        return getRequest().getMultipart() != null;
    }

    public ByteSource getBodyAsStream()
    {
        final Request request = getRequest();
        return request.getBody();
    }

    public String getBodyAsString()
        throws Exception
    {
        if ( isTextBody() )
        {
            return getBodyAsStream().asCharSource( Charsets.UTF_8 ).read();
        }

        return null;
    }
}
