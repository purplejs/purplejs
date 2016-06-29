package io.purplejs.http.executor;

import java.util.function.Consumer;

import io.purplejs.EngineBuilder;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.impl.HttpExecutorBuilderImpl;

public interface HttpExecutorBuilder
{
    HttpExecutorBuilder engine( Consumer<EngineBuilder> configurator );

    HttpExecutorBuilder exceptionHandler( ExceptionHandler exceptionHandler );

    HttpExecutor build();

    static HttpExecutorBuilder newBuilder()
    {
        return new HttpExecutorBuilderImpl();
    }
}
