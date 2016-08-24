package io.purplejs.boot.internal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import io.purplejs.boot.internal.request.RequestImpl;
import io.purplejs.boot.internal.response.ResponseSerializer;
import io.purplejs.boot.internal.websocket.WebSocketHandler;
import io.purplejs.core.Engine;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.handler.HttpHandlerFactory;
import io.purplejs.http.websocket.WebSocketConfig;

public final class ScriptServlet
    extends HttpServlet
{
    Engine engine;

    HttpHandler handler;

    private WebSocketServletFactory webSocketServletFactory;

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

        initWebSocketFactory();
    }

    private void initWebSocketFactory()
        throws ServletException
    {
        try
        {
            this.webSocketServletFactory = new WebSocketServerFactory();
            this.webSocketServletFactory.getPolicy().setIdleTimeout( 10000 );
            this.webSocketServletFactory.init( getServletContext() );
        }
        catch ( final Exception e )
        {
            throw new ServletException( e );
        }
    }

    @Override
    protected void service( final HttpServletRequest req, final HttpServletResponse res )
        throws ServletException, IOException
    {
        final RequestImpl actualRequest = new RequestImpl( req );

        final boolean isWebSocket = this.webSocketServletFactory.isUpgradeRequest( req, res );
        actualRequest.setWebSocket( isWebSocket );

        final Response actualResponse = serve( actualRequest );
        if ( actualResponse == null )
        {
            return;
        }

        if ( !isWebSocket )
        {
            new ResponseSerializer( res ).serialize( actualResponse );
            return;
        }

        final WebSocketConfig config = actualResponse.getWebSocket();
        acceptWebSocket( req, res, config );
    }

    private Response serve( final Request request )
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

    private void acceptWebSocket( final HttpServletRequest req, final HttpServletResponse res, final WebSocketConfig config )
        throws IOException
    {
        final WebSocketCreator creator = new WebSocketHandler( this.handler, config );
        this.webSocketServletFactory.acceptWebSocket( creator, req, res );
    }

    @Override
    public void destroy()
    {
        this.engine.dispose();
    }
}
