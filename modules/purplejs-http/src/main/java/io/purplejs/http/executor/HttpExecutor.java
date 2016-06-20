package io.purplejs.http.executor;

import io.purplejs.Engine;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.resource.ResourcePath;

public interface HttpExecutor
{
    Engine getEngine();

    Response serve( ResourcePath resource, Request request );

    void dispose();
}
