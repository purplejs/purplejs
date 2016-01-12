package org.purplejs.engine;

import java.util.function.Supplier;

import org.purplejs.http.Request;
import org.purplejs.resource.ResourceResolver;

public interface EngineContext
{
    Engine getEngine();

    Supplier<Request> getRequest();

    ResourceResolver getResourceResolver();

    static EngineContext get()
    {
        return null;
    }
}
