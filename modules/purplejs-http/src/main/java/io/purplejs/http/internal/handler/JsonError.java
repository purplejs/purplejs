package io.purplejs.http.internal.handler;

import io.purplejs.core.json.JsonGenerator;
import io.purplejs.core.json.JsonSerializable;
import io.purplejs.http.Request;
import io.purplejs.http.Status;

final class JsonError
    implements JsonSerializable
{
    private final Status status;

    private final Throwable cause;

    private final Request request;

    JsonError( final Status status, final Throwable cause, final Request request )
    {
        this.status = status;
        this.cause = cause;
        this.request = request;
    }

    @Override
    public void serialize( final JsonGenerator gen )
    {
        gen.map();
        gen.value( "status", this.status.getCode() );
        gen.value( "message", getMessage() );
        gen.value( "exception", this.cause );
        gen.value( "request", new JsonRequest( this.request ) );
        gen.end();
    }

    private String getMessage()
    {
        if ( this.cause != null )
        {
            return this.cause.getMessage();
        }
        else
        {
            return this.status.getReasonPhrase();
        }
    }
}
