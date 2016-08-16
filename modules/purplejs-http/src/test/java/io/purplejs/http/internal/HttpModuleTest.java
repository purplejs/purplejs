package io.purplejs.http.internal;

import org.junit.Test;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBuilder;
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
