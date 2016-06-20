package io.purplejs.http.impl.response;

import com.google.common.net.MediaType;

import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.value.ScriptValue;

public final class ScriptToResponse
{
    public Response toResponse( final ScriptValue value )
    {
        final ResponseBuilder builder = ResponseBuilder.newBuilder();
        populateStatus( builder, value.getMember( "status" ) );
        populateContentType( builder, value.getMember( "contentType" ) );
        populateBody( builder, value.getMember( "body" ) );

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
        if ( ( value == null ) || value.isFunction() )
        {
            return;
        }

        if ( value.isArray() )
        {
            builder.body( value.getValue( String.class ) );
            return;
        }

        if ( value.isObject() )
        {
            builder.body( value.getMap() );
            return;
        }

        builder.body( value.getValue() );
    }
}
