package io.purplejs.http.internal.handler;

import java.util.function.Function;

import io.purplejs.core.value.ScriptExports;
import io.purplejs.core.value.ScriptValue;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.internal.RequestAccessor;
import io.purplejs.http.internal.response.ScriptToResponse;

abstract class BaseRequestCommand
    implements Function<ScriptExports, Response>
{
    protected final Request request;

    BaseRequestCommand( final Request request )
    {
        this.request = request;
    }

    @Override
    public final Response apply( final ScriptExports exports )
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

    protected abstract Response doExecute( ScriptExports exports );

    protected final Response executeMethod(final ScriptExports exports, final String name, final Object... args)
    {
        final ScriptValue value = exports.executeMethod( name, args );
        return new ScriptToResponse().toResponse( value );
    }
}
