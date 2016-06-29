package io.purplejs.http.executor;

import io.purplejs.http.Request;
import io.purplejs.http.Response;

public interface HttpHandler
{
    Response serve( Request request );
}
