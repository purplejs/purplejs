package io.purplejs.http.impl;

import io.purplejs.Engine;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.executor.HttpExecutor;
import io.purplejs.http.executor.HttpHandler;
import io.purplejs.resource.ResourcePath;

final class HttpExecutorImpl
    implements HttpExecutor
{
    Engine engine;

    ExceptionHandler exceptionHandler;

    @Override
    public Engine getEngine()
    {
        return this.engine;
    }

    @Override
    public HttpHandler newHandler( final ResourcePath resource )
    {
        final HttpHandlerImpl handler = new HttpHandlerImpl();
        handler.engine = this.engine;
        handler.exceptionHandler = this.exceptionHandler;
        handler.resource = resource;

        handler.init();
        return handler;
    }

    @Override
    public void dispose()
    {
        this.engine.dispose();
    }
}
