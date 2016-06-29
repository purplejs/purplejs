package io.purplejs.http.impl.lib;

import com.google.common.base.Charsets;
import com.google.common.net.MediaType;

import io.purplejs.http.Request;

public final class RequestHelper
{
    public static boolean isJsonBody( final Request request )
    {
        final MediaType type = request.getContentType();
        if ( type == null )
        {
            return false;
        }

        final String subType = type.subtype();
        return subType.equalsIgnoreCase( "json" ) || subType.toLowerCase().startsWith( "json-" );
    }

    public static boolean isTextBody( final Request request )
    {
        final MediaType type = request.getContentType();
        if ( type == null )
        {
            return false;
        }

        return type.withoutParameters().is( MediaType.ANY_TEXT_TYPE ) || isJsonBody( request );
    }

    public static String bodyAsString( final Request request )
        throws Exception
    {
        if ( request.getContentLength() <= 0 )
        {
            return null;
        }

        if ( !isTextBody( request ) )
        {
            return null;
        }

        return request.getBody().asCharSource( Charsets.UTF_8 ).read();
    }
}
