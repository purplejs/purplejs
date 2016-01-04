package org.purplejs.engine;

import org.purplejs.registry.Registry;

public final class EngineBuilder
{
    public <T> EngineBuilder registry( final Registry registry )
    {
        return this;
    }

    public Engine build()
    {
        return null;
    }
}
