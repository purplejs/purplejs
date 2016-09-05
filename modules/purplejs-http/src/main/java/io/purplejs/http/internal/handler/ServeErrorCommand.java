package io.purplejs.http.internal.handler;

import io.purplejs.core.value.ScriptExports;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.Status;

final class ServeErrorCommand
    extends BaseRequestCommand
{
    private final static String HANDLE_PREFIX = "handle";

    private final static String GENERAL_HANDLE = HANDLE_PREFIX + "Error";

    private final Status status;

    private final Throwable cause;

    ServeErrorCommand( final Request request, final Status status, final Throwable cause )
    {
        super( request );
        this.status = status;
        this.cause = cause;
    }

    @Override
    protected Response doExecute( final ScriptExports exports )
    {
        final String method = findMethod( exports );
        if ( method == null )
        {
            return null;
        }

        final JsonError wrapper = new JsonError( this.status, this.cause, this.request );
        return executeMethod( exports, method, wrapper );
    }

    private String findMethod( final ScriptExports exports )
    {
        final String name = HANDLE_PREFIX + status.getCode();
        if ( exports.hasMethod( name ) )
        {
            return name;
        }

        if ( exports.hasMethod( GENERAL_HANDLE ) )
        {
            return GENERAL_HANDLE;
        }

        return null;
    }
}
