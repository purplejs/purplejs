package io.purplejs.http.executor;

import io.purplejs.Engine;
import io.purplejs.resource.ResourcePath;

public interface HttpExecutor
{
    Engine getEngine();

    HttpHandler newHandler( ResourcePath resource );

    void dispose();
}
