package org.purplejs.engine;

import org.purplejs.resource.ResourceLoader;

public interface EngineContext
{
    Engine getEngine();

    // Supplier<Request> getRequest();

    ResourceLoader getResourceResolver();

    static EngineContext get()
    {
        return null;
    }
}
