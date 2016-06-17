package io.purplejs;

import java.io.File;

import io.purplejs.resource.ResourceLoader;

import io.purplejs.impl.EngineBuilderImpl;

public interface EngineBuilder
{
    EngineBuilder devMode( boolean devMode );

    EngineBuilder devSourceDir( File dir );

    EngineBuilder classLoader( ClassLoader classLoader );

    EngineBuilder resourceLoader( ResourceLoader resourceLoader );

    EngineBuilder globalVariable( String name, Object value );

    EngineBuilder config( String name, String value );

    Engine build();

    static EngineBuilder newBuilder()
    {
        return new EngineBuilderImpl();
    }
}
