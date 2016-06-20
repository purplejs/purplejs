package io.purplejs.http.impl;

import io.purplejs.Engine;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.error.ErrorHandler;
import io.purplejs.http.executor.HttpExecutor;
import io.purplejs.http.impl.error.DefaultErrorHandler;
import io.purplejs.resource.ResourcePath;

final class HttpExecutorImpl
    implements HttpExecutor
{
    private final Engine engine;

    private final ErrorHandler handler;

    HttpExecutorImpl( final Engine engine )
    {
        this.engine = engine;
        this.handler = new DefaultErrorHandler();
    }

    @Override
    public Engine getEngine()
    {
        return this.engine;
    }

    @Override
    public Response serve( final ResourcePath resource, final Request request )
    {
        try
        {
            return doServe( resource, request );
        }
        catch ( final Exception e )
        {
            return this.handler.handleException( e );
        }
    }

    private Response doServe( final ResourcePath resource, final Request request )
    {
        return this.engine.execute( resource, ( exports ) -> null );
    }

    @Override
    public void dispose()
    {
        this.engine.dispose();
    }
}
