package io.purplejs.core.inject;

import io.purplejs.core.Engine;
import io.purplejs.core.resource.ResourcePath;

public interface InjectorContext
{
    Engine getEngine();

    ResourcePath getResource();

    Object getInstance();
}
