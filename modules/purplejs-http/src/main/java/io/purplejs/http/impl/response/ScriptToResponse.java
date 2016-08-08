package io.purplejs.http.impl.response;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

import io.purplejs.http.Cookie;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.value.ScriptValue;

public final class ScriptToResponse
{
    public Response toResponse( final ScriptValue value )
    {
        final ResponseBuilder builder = ResponseBuilder.newBuilder();
        if ( value == null )
        {
            return builder.build();
        }

        builder.value( value );
        populateStatus( builder, value.getMember( "status" ) );
        populateContentType( builder, value.getMember( "contentType" ) );
        populateBody( builder, value.getMember( "body" ) );
        populateHeaders( builder, value.getMember( "headers" ) );
        populateCookies( builder, value.getMember( "cookies" ) );
        setRedirect( builder, value.getMember( "redirect" ) );

        return builder.build();
    }

    private void populateStatus( final ResponseBuilder builder, final ScriptValue value )
    {
        final Integer status = ( value != null ) ? value.getValue( Integer.class ) : null;
        builder.status( status != null ? Status.from( status ) : Status.OK );
    }

    private void populateContentType( final ResponseBuilder builder, final ScriptValue value )
    {
        final String type = ( value != null ) ? value.getValue( String.class ) : null;
        builder.contentType( type != null ? MediaType.parse( type ) : MediaType.create( "text", "html" ) );
    }

    private void populateBody( final ResponseBuilder builder, final ScriptValue value )
    {
        builder.body( new BodySerializer().toBody( value ) );
    }

    private void populateHeaders( final ResponseBuilder builder, final ScriptValue value )
    {
        if ( value == null )
        {
            return;
        }

        if ( !value.isObject() )
        {
            return;
        }

        for ( final String key : value.getKeys() )
        {
            builder.header( key, value.getMember( key ).getValue( String.class ) );
        }
    }

    private void setRedirect( final ResponseBuilder builder, final ScriptValue value )
    {
        final String redirect = ( value != null ) ? value.getValue( String.class ) : null;
        if ( redirect == null )
        {
            return;
        }

        builder.status( Status.SEE_OTHER );
        builder.header( HttpHeaders.LOCATION, redirect );
    }

    private void populateCookies( final ResponseBuilder builder, final ScriptValue value )
    {
        if ( value == null )
        {
            return;
        }

        for ( final String key : value.getKeys() )
        {
            addCookie( builder, value.getMember( key ), key );
        }
    }

    private <T> T getValue( final ScriptValue value, final Class<T> type, final T defValue )
    {
        if ( value == null )
        {
            return null;
        }

        final T result = value.getValue( type );
        return result != null ? result : defValue;
    }

    private <T> T getMemberValue( final ScriptValue value, final String name, final Class<T> type, final T defValue )
    {
        final T result = getValue( value.getMember( name ), type, defValue );
        return result != null ? result : defValue;
    }

    private Cookie newCookie( final ScriptValue value, final String key )
    {
        final CookieImpl cookie = new CookieImpl();
        cookie.name = key;

        if ( value.isObject() )
        {
            cookie.value = getMemberValue( value, "value", String.class, "" );
            cookie.path = getMemberValue( value, "path", String.class, null );
            cookie.domain = getMemberValue( value, "domain", String.class, null );
            cookie.comment = getMemberValue( value, "comment", String.class, null );
            cookie.maxAge = getMemberValue( value, "maxAge", Integer.class, -1 );
            cookie.secure = getMemberValue( value, "secure", Boolean.class, false );
            cookie.httpOnly = getMemberValue( value, "httpOnly", Boolean.class, false );
        }
        else
        {
            cookie.value = getValue( value, String.class, "" );
        }

        return cookie;
    }

    private void addCookie( final ResponseBuilder builder, final ScriptValue value, final String key )
    {
        if ( value != null )
        {
            builder.cookie( newCookie( value, key ) );
        }
    }
}
