package io.purplejs.http.handler;

import io.purplejs.http.Request;
import io.purplejs.http.Response;

public interface HttpHandler
{
    Response serve( Request request );

    // TODO: Remove exception handling in "serve" and move it outside here into other method.
}
