package io.purplejs.http.impl;

import io.purplejs.Engine;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.executor.HttpExecutor;
import io.purplejs.http.impl.command.ServeRequestCommand;
import io.purplejs.resource.ResourcePath;

final class HttpExecutorImpl
    implements HttpExecutor
{
    private final Engine engine;

    HttpExecutorImpl( final Engine engine )
    {
        this.engine = engine;
    }

    @Override
    public Engine getEngine()
    {
        return this.engine;
    }

    @Override
    public Response serve( final ResourcePath resource, final Request request )
    {
        return this.engine.execute( resource, new ServeRequestCommand( request ) );
    }

    @Override
    public void dispose()
    {
        this.engine.dispose();
    }
}
