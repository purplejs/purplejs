package io.purplejs.http.internal.error;

import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.Status;

public interface ErrorRenderer
{
    Response handle( Request request, Throwable ex );

    Response handle( Request request, Status status );
}
