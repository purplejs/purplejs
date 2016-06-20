package io.purplejs.http.impl.command;

import java.util.function.Function;

import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.Status;
import io.purplejs.http.impl.response.ResponseBuilder;
import io.purplejs.http.impl.response.ScriptToResponse;
import io.purplejs.value.ScriptExports;
import io.purplejs.value.ScriptValue;

public final class ServeRequestCommand
    implements Function<ScriptExports, Response>
{
    private final static String SERVICE_METHOD = "service";

    private final Request request;

    public ServeRequestCommand( final Request request )
    {
        this.request = request;
    }

    @Override
    public Response apply( final ScriptExports exports )
    {
        final String method = findMethod( exports );
        if ( method == null )
        {
            return methodNotAllowed();
        }

        final ScriptValue value = exports.executeMethod( method, this.request );
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

        return null;
    }

    private Response methodNotAllowed()
    {
        return ResponseBuilder.newBuilder().
            status( Status.METHOD_NOT_ALLOWED ).
            build();
    }
}
