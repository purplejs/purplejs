package io.purplejs.http.impl.request;

import java.net.HttpCookie;
import java.util.Collection;
import java.util.Map;

import com.google.common.net.HttpHeaders;

import io.purplejs.http.Headers;
import io.purplejs.http.Parameters;
import io.purplejs.http.Request;
import io.purplejs.core.json.JsonGenerator;
import io.purplejs.core.json.JsonSerializable;

public final class RequestWrapper
    implements JsonSerializable
{
    private final Request request;

    public RequestWrapper( final Request request )
    {
        this.request = request;
    }

    @Override
    public void serialize( final JsonGenerator gen )
    {
        gen.map();
        gen.value( "method", this.request.getMethod() );

        gen.value( "scheme", this.request.getUri().getScheme() );
        gen.value( "host", this.request.getUri().getHost() );
        gen.value( "port", this.request.getUri().getPort() );
        gen.value( "path", this.request.getUri().getPath() );
        gen.value( "uri", this.request.getUri() );
        gen.value( "contentType", this.request.getContentType() );
        gen.value( "contentLength", this.request.getContentLength() );

        //gen.value( "remoteAddress", this.request.getRemoteAddress() );
        //gen.value( "webSocket", this.request.isWebSocket() );

        serializeParameters( gen, this.request.getParameters() );
        serializeHeaders( gen, this.request.getHeaders() );
        serializeCookies( gen, this.request.getHeaders().get( HttpHeaders.COOKIE ) );

        gen.end();
    }

    private void serializeParameters( final JsonGenerator gen, final Parameters params )
    {
        gen.map( "params" );
        for ( final Map.Entry<String, Collection<String>> entry : params.asMap().entrySet() )
        {
            final Collection<String> values = entry.getValue();
            if ( values.size() == 1 )
            {
                gen.value( entry.getKey(), values.iterator().next() );
            }
            else
            {
                gen.array( entry.getKey() );
                values.forEach( gen::value );
                gen.end();
            }
        }
        gen.end();
    }

    private boolean shouldSerializeHeader( final String name )
    {
        return !name.equalsIgnoreCase( HttpHeaders.COOKIE );
    }

    private void serializeHeaders( final JsonGenerator gen, final Headers headers )
    {
        gen.map( "headers" );
        headers.entrySet().stream().filter( entry -> shouldSerializeHeader( entry.getKey() ) ).forEach(
            entry -> gen.value( entry.getKey(), entry.getValue() ) );
        gen.end();
    }

    private void serializeCookies( final JsonGenerator gen, final String value )
    {
        gen.map( "cookies" );
        if ( value != null )
        {
            for ( final HttpCookie cookie : HttpCookie.parse( value ) )
            {
                gen.value( cookie.getName(), cookie.getValue() );
            }
        }

        gen.end();
    }
}
