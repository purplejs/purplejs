package io.purplejs.http.error;

import io.purplejs.http.Response;

public interface ErrorHandler
{
    Response handle( ErrorInfo ex );
}
