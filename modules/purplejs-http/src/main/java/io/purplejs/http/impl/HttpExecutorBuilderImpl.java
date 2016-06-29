package io.purplejs.http.impl;

import java.util.function.Consumer;

import io.purplejs.EngineBuilder;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.executor.HttpExecutor;
import io.purplejs.http.executor.HttpExecutorBuilder;
import io.purplejs.http.impl.error.DefaultExceptionHandler;

public final class HttpExecutorBuilderImpl
    implements HttpExecutorBuilder
{
    private final EngineBuilder engineBuilder;

    private ExceptionHandler exceptionHandler;

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
    public HttpExecutorBuilder exceptionHandler( final ExceptionHandler exceptionHandler )
    {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    private void setupDefaults()
    {
        if ( this.exceptionHandler == null )
        {
            this.exceptionHandler = new DefaultExceptionHandler();
        }
    }

    @Override
    public HttpExecutor build()
    {
        setupDefaults();

        final HttpExecutorImpl executor = new HttpExecutorImpl();
        executor.engine = this.engineBuilder.build();
        executor.exceptionHandler = this.exceptionHandler;
        return executor;
    }
}
