package io.purplejs.http.error;

import io.purplejs.http.Response;

public interface ExceptionHandler
{
    Response handle( ExceptionInfo ex );
}
