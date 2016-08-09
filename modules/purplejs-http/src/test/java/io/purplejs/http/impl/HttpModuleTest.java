package io.purplejs.http.impl;

import org.junit.Test;

import io.purplejs.Engine;
import io.purplejs.EngineBuilder;
import io.purplejs.http.handler.HttpHandlerFactory;

import static org.junit.Assert.*;

public class HttpModuleTest
{
    @Test
    public void testModule()
    {
        final Engine engine = EngineBuilder.newBuilder().
            module( new HttpModule() ).
            build();

        final HttpHandlerFactory factory = engine.getInstance( HttpHandlerFactory.class );
        assertNotNull( factory );
    }
}
