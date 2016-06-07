package org.purplejs.engine;

import org.purplejs.impl.engine.EngineBuilderImpl;
import org.purplejs.resource.ResourceLoader;

public interface EngineBuilder
{
    EngineBuilder devMode( boolean devMode );

    EngineBuilder classLoader( ClassLoader classLoader );

    EngineBuilder resourceLoader( ResourceLoader resourceLoader );

    EngineBuilder globalVariable( String name, Object value );

    EngineBuilder config( String name, String value );

    Engine build();

    static EngineBuilder create()
    {
        return new EngineBuilderImpl();
    }
}
