package io.purplejs.boot.internal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

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
        // See WebSocketServlet for how to do websockets...

        try
        {
            final WebSocketPolicy policy = new WebSocketPolicy( WebSocketBehavior.SERVER );
            this.webSocketServletFactory = WebSocketServletFactory.Loader.create( policy );
            this.webSocketServletFactory.getPolicy().setIdleTimeout( 10000 );
            this.webSocketServletFactory.init( getServletContext() );
        }
        catch ( final Exception e )
        {
            throw new ServletException( e );
        }
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


        // TODO: Custom serialize Object's in JsonSerializer (like session, ByteStream etc)
        /**
         *
         * return {
         *   webSocket: {
         *     ... settings ...
         *     attributes: {
         *       ... custom attributes ...
         *     }
         *   }
         * };
         *
         * onEvent(event)
         *
         *   event.type
         *   event.session
         *   event.sessionId
         *   event.message
         *
         *   event.session.send(...);
         *
         *
         * }
         *
         */
    }

    @Override
    public void destroy()
    {
        this.engine.dispose();
    }
}
