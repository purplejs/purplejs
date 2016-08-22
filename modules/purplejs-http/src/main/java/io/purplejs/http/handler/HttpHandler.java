package io.purplejs.http.handler;

import io.purplejs.http.Request;
import io.purplejs.http.Response;

public interface HttpHandler
{
    Response serve( Request request );

    Response errorIfNeeded( Request request, Response response );

    Response handleException( Request request, Throwable cause );
}
