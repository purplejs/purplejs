package org.purplejs.bean;

import org.purplejs.engine.Engine;
import org.purplejs.registry.Binding;
import org.purplejs.registry.Registry;
import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceLoader;

public interface BeanContext
{
    Engine getEngine();

    Registry getRegistry();

    Resource getResource();

    ResourceLoader getLoader();

    <T> Binding<T> lookup( Class<T> type );
}
