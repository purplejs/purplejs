package io.purplejs.http.error;

import java.util.List;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.http.Request;
import io.purplejs.http.Status;

public interface ErrorInfo
{
    Status getStatus();

    Throwable getCause();

    Request getRequest();

    ResourcePath getPath();

    List<String> getLines();
}
