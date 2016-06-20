package io.purplejs.http.error;

import java.util.List;

import io.purplejs.http.Request;
import io.purplejs.http.Status;
import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;

public interface ExceptionInfo
{
    Status getStatus();

    Throwable getCause();

    Request getRequest();

    ResourcePath getPath();

    Resource getResource();

    List<String> getLines();
}
