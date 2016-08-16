package io.purplejs.http.error;

import java.util.List;

import io.purplejs.http.Request;
import io.purplejs.http.Status;
import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourcePath;

public interface ExceptionInfo
{
    Status getStatus();

    Throwable getCause();

    Request getRequest();

    ResourcePath getPath();

    Resource getResource();

    List<String> getLines();
}
