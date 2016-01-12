package org.purplejs.engine;

import org.purplejs.impl.engine.EngineImpl;

public final class EngineBuilder
{
    private final EngineImpl engine;

    public EngineBuilder()
    {
        this.engine = new EngineImpl();
    }

    public Engine build()
    {
        return this.engine;
    }
}
