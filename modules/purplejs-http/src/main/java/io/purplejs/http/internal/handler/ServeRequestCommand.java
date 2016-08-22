package io.purplejs.http.internal.handler;

import java.util.function.Function;

import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.ResponseBuilder;
import io.purplejs.http.Status;
import io.purplejs.http.internal.RequestAccessor;
import io.purplejs.http.internal.request.JsonRequest;
import io.purplejs.http.internal.response.ScriptToResponse;
import io.purplejs.core.value.ScriptExports;
import io.purplejs.core.value.ScriptValue;

final class ServeRequestCommand
    implements Function<ScriptExports, Response>
{
    private final static String GET_METHOD = "get";

    private final static String HEAD_METHOD = "head";

    private final static String SERVICE_METHOD = "service";

    private final Request request;

    ServeRequestCommand( final Request request )
    {
        this.request = request;
    }

    @Override
    public Response apply( final ScriptExports exports )
    {
        try
        {
            RequestAccessor.set( this.request );
            return doExecute( exports );
        }
        finally
        {
            RequestAccessor.remove();
        }
    }

    private Response doExecute( final ScriptExports exports )
    {
        final String method = findMethod( exports );
        if ( method == null )
        {
            return methodNotAllowed();
        }

        final JsonRequest wrapper = new JsonRequest( this.request );
        final ScriptValue value = exports.executeMethod( method, wrapper );
        return new ScriptToResponse().toResponse( value );
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
