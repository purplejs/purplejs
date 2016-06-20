package io.purplejs.http.executor;

import java.util.function.Consumer;

import io.purplejs.EngineBuilder;
import io.purplejs.http.impl.HttpExecutorBuilderImpl;

public interface HttpExecutorBuilder
{
    HttpExecutorBuilder engine( Consumer<EngineBuilder> configurator );

    HttpExecutor build();

    static HttpExecutorBuilder newBuilder()
    {
        return new HttpExecutorBuilderImpl();
    }
}
