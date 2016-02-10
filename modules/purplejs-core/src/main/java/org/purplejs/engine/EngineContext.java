package org.purplejs.engine;

import org.purplejs.resource.ResourceResolver;

public interface EngineContext
{
    Engine getEngine();

    // Supplier<Request> getRequest();

    ResourceResolver getResourceResolver();

    static EngineContext get()
    {
        return null;
    }
}
