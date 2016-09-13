package io.purplejs.http.itest

import com.google.common.base.Charsets
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import io.purplejs.core.itest.AbstractCoreITest
import io.purplejs.core.resource.ResourcePath
import io.purplejs.http.Request
import io.purplejs.http.RequestBuilder
import io.purplejs.http.Response
import io.purplejs.http.handler.HttpHandler
import io.purplejs.http.handler.HttpHandlerFactory
import io.purplejs.http.websocket.WebSocketEvent

abstract class AbstractHttpITest
    extends AbstractCoreITest
{
    protected HttpHandlerFactory handlerFactory;

    protected RequestBuilder requestBuilder;

    public void setup()
    {
        this.handlerFactory = this.engine.getInstance( HttpHandlerFactory.class );
        this.requestBuilder = RequestBuilder.newBuilder();
    }

    protected final Response serve( final String path, final Request request )
    {
        return serve( ResourcePath.from( path ), request );
    }

    protected final Response serve( final ResourcePath path, final Request request )
    {
        return this.handlerFactory.newHandler( path ).serve( request );
    }

    public void assertEquals( final Object expected, final Object actual )
    {
        assert expected == actual;
    }

    protected final void script( final String content )
    {
        file( '/test.js', content );
    }

    protected final boolean handleEvent( final WebSocketEvent event )
    {
        return this.handlerFactory.newHandler( ResourcePath.from( '/test.js' ) ).handleEvent( event );
    }

    protected final Response serve()
    {
        return serve( '/test.js', this.requestBuilder.build() );
    }

    protected final static String toStringBody( final Response response )
    {
        return response.body ? response.body.asCharSource( Charsets.UTF_8 ).read() : null;
    }

    protected final static String prettifyJson( final String json )
    {
        final Gson gson = new GsonBuilder().
            setPrettyPrinting().
            create();

        return gson.toJson( gson.fromJson( json.trim(), JsonElement.class ) );
    }
}
