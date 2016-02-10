package org.purplejs.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.purplejs.engine.Engine;
import org.purplejs.engine.EngineBuilder;

public class ScriptServlet
    extends HttpServlet
{
    private Engine engine;

    @Override
    public void init( final ServletConfig config )
        throws ServletException
    {
        super.init( config );

        this.engine = new EngineBuilder().
            build();
    }
}
