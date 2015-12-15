package org.purplejs;

import org.purplejs.convert.Converters;

public final class EngineBuilder
{
    private Converters converters;

    public EngineBuilder()
    {
        this.converters = new Converters();
    }

    public EngineBuilder converters( final Converters converters )
    {
        this.converters = converters;
        return this;
    }

    public Engine build()
    {
        return null;
    }
}
