package io.purplejs.http.impl;

import java.util.function.Consumer;

import io.purplejs.Engine;
import io.purplejs.EngineBuilder;
import io.purplejs.http.executor.HttpExecutor;
import io.purplejs.http.executor.HttpExecutorBuilder;

public final class HttpExecutorBuilderImpl
    implements HttpExecutorBuilder
{
    private final EngineBuilder engineBuilder;

    public HttpExecutorBuilderImpl()
    {
        this.engineBuilder = EngineBuilder.newBuilder();
    }

    @Override
    public HttpExecutorBuilder engine( final Consumer<EngineBuilder> configurator )
    {
        configurator.accept( this.engineBuilder );
        return this;
    }

    @Override
    public HttpExecutor build()
    {
        final Engine engine = this.engineBuilder.build();
        return new HttpExecutorImpl( engine );
    }
}
