package io.purplejs.http.impl.error;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.http.Request;
import io.purplejs.http.Status;
import io.purplejs.http.error.ExceptionInfo;
import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourcePath;

final class ExceptionInfoImpl
    implements ExceptionInfo
{
    Status status;

    Throwable cause;

    Request request;

    ResourcePath path;

    Resource resource;

    List<String> lines;

    @Override
    public Status getStatus()
    {
        return this.status;
    }

    @Override
    public Throwable getCause()
    {
        return this.cause;
    }

    @Override
    public Request getRequest()
    {
        return this.request;
    }

    @Override
    public ResourcePath getPath()
    {
        return this.path;
    }

    @Override
    public Resource getResource()
    {
        return this.resource;
    }

    @Override
    public List<String> getLines()
    {
        return this.lines != null ? this.lines : Lists.newArrayList();
    }
}
