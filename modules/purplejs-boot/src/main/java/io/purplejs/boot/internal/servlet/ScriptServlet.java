package io.purplejs.boot.internal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.purplejs.boot.internal.request.RequestImpl;
import io.purplejs.boot.internal.response.ResponseSerializer;
import io.purplejs.core.Engine;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.http.Response;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.handler.HttpHandlerFactory;

public final class ScriptServlet
    extends HttpServlet
{
    private Engine engine;

    private HttpHandler handler;

    public void setEngine( final Engine engine )
    {
        this.engine = engine;
    }

    @Override
    public void init()
        throws ServletException
    {
        super.init();

        final HttpHandlerFactory handlerFactory = this.engine.getInstance( HttpHandlerFactory.class );
        this.handler = handlerFactory.newHandler( ResourcePath.from( "/app/main.js" ) );
    }

    @Override
    protected void service( final HttpServletRequest req, final HttpServletResponse resp )
        throws ServletException, IOException
    {
        final RequestImpl actualRequest = new RequestImpl( req );
        final Response actualResponse = serve( actualRequest );

        new ResponseSerializer( resp ).serialize( actualResponse );
    }

    private Response serve( final io.purplejs.http.Request request )
    {
        try
        {
            final Response response = this.handler.serve( request );
            return this.handler.errorIfNeeded( request, response );
        }
        catch ( final Exception e )
        {
            return this.handler.handleException( request, e );
        }
    }

    @Override
    public void destroy()
    {
        this.engine.dispose();
    }
}
