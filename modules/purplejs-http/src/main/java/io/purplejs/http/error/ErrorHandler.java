package io.purplejs.http.error;

import io.purplejs.http.Response;

public interface ErrorHandler
{
    Response handleException( Exception ex );
}
