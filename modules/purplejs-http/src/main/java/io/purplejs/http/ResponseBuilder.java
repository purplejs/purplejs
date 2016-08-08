package io.purplejs.http;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.http.impl.response.ResponseBuilderImpl;
import io.purplejs.value.ScriptValue;

public interface ResponseBuilder
{
    ResponseBuilder status( Status status );

    ResponseBuilder contentType( MediaType contentType );

    ResponseBuilder body( ByteSource body );

    ResponseBuilder header( String name, String value );

    ResponseBuilder value( ScriptValue value );

    ResponseBuilder cookie( Cookie cookie );

    Response build();

    static ResponseBuilder newBuilder()
    {
        return new ResponseBuilderImpl();
    }
}
