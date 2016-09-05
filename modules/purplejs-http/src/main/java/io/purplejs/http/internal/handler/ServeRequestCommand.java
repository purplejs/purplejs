package io.purplejs.http.internal.handler;

import io.purplejs.core.value.ScriptExports;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.ResponseBuilder;
import io.purplejs.http.Status;

final class ServeRequestCommand
    extends BaseRequestCommand
{
    private final static String GET_METHOD = "get";

    private final static String HEAD_METHOD = "head";

    private final static String SERVICE_METHOD = "service";

    ServeRequestCommand( final Request request )
    {
        super( request );
    }

    @Override
    protected Response doExecute( final ScriptExports exports )
    {
        final String method = findMethod( exports );
        if ( method == null )
        {
            return methodNotAllowed();
        }

        final JsonRequest wrapper = new JsonRequest( this.request );
        return executeMethod( exports, method, wrapper );
    }

    private String findMethod( final ScriptExports exports )
    {
        final String method = this.request.getMethod().toLowerCase();
        if ( exports.hasMethod( method ) )
        {
            return method;
        }

        if ( exports.hasMethod( SERVICE_METHOD ) )
        {
            return SERVICE_METHOD;
        }

        if ( method.equals( HEAD_METHOD ) )
        {
            if ( exports.hasMethod( GET_METHOD ) )
            {
                return GET_METHOD;
            }
        }

        return null;
    }

    private Response methodNotAllowed()
    {
        return ResponseBuilder.newBuilder().
            status( Status.METHOD_NOT_ALLOWED ).
            build();
    }
}
