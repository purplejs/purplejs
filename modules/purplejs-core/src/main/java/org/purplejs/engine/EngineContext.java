package org.purplejs.engine;

import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourcePath;

// TODO: Rename to execute context
public interface EngineContext
{
    ResourcePath getResource();

    ResourceLoader getLoader();

    // <T> Supplier<T> lookup( Class<T> type );


}
