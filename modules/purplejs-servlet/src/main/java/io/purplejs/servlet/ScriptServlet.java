package io.purplejs.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.purplejs.EngineBuilder;
import io.purplejs.http.Response;
import io.purplejs.http.executor.HttpExecutor;
import io.purplejs.http.executor.HttpExecutorBuilder;
import io.purplejs.resource.ResourcePath;
import io.purplejs.servlet.impl.RequestWrapper;
import io.purplejs.servlet.impl.ResponseSerializer;

public class ScriptServlet
    extends HttpServlet
{
    private HttpExecutor executor;

    private ResourcePath resource;

    @Override
    public void init( final ServletConfig config )
        throws ServletException
    {
        super.init( config );
        configure( new ScriptServletConfig( config ) );
    }

    private void configure( final ScriptServletConfig config )
    {
        this.resource = config.getResource();

        final HttpExecutorBuilder builder = HttpExecutorBuilder.newBuilder();
        builder.engine( ( engineBuilder ) -> configure( config, engineBuilder ) );

        this.executor = builder.build();
    }

    private void configure( final ScriptServletConfig config, final EngineBuilder builder )
    {
        builder.classLoader( getClass().getClassLoader() );
        config.getConfig().forEach( builder::config );
    }

    @Override
    protected void service( final HttpServletRequest req, final HttpServletResponse resp )
        throws ServletException, IOException
    {
        final RequestWrapper requestWrapper = new RequestWrapper( req );
        final Response response = this.executor.serve( this.resource, requestWrapper );
        serialize( resp, response );
    }

    private void serialize( final HttpServletResponse to, final Response from )
        throws IOException
    {
        new ResponseSerializer( to ).serialize( from );
    }

    @Override
    public void destroy()
    {
        this.executor.dispose();
    }
}
