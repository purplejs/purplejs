package io.purplejs.http.internal.error;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.http.Request;
import io.purplejs.http.Status;
import io.purplejs.http.error.ErrorInfo;

final class ErrorInfoImpl
    implements ErrorInfo
{
    Status status;

    Throwable cause;

    Request request;

    ResourcePath path;

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
    public List<String> getLines()
    {
        return this.lines != null ? this.lines : Lists.newArrayList();
    }
}
