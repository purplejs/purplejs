package io.purplejs.http.error;

import io.purplejs.http.Response;

// TODO: Rename to ErrorHandler and ErrorInfo.
public interface ExceptionHandler
{
    Response handle( ExceptionInfo ex );
}
