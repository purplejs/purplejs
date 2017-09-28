package io.purplejs.core.internal.inject;

import io.purplejs.core.Engine;
import io.purplejs.core.inject.InjectorContext;
import io.purplejs.core.resource.ResourcePath;

public final class InjectorContextImpl
    implements InjectorContext
{
    private final Engine engine;

    private final Object instance;

    private final ResourcePath resourcePath;

    public InjectorContextImpl( final Engine engine, final ResourcePath resourcePath, final Object instance )
    {
        this.engine = engine;
        this.resourcePath = resourcePath;
        this.instance = instance;
    }

    @Override
    public Engine getEngine()
    {
        return this.engine;
    }

    @Override
    public ResourcePath getResource()
    {
        return this.resourcePath;
    }

    @Override
    public Object getInstance()
    {
        return this.instance;
    }

}
