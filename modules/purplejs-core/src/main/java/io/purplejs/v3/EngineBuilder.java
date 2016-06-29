package io.purplejs.v3;

import java.io.File;

import io.purplejs.resource.ResourceLoader;

public interface EngineBuilder
{
    EngineBuilder module( EngineModule module );

    EngineBuilder devMode( boolean devMode );

    EngineBuilder devSourceDir( File dir );

    EngineBuilder classLoader( ClassLoader classLoader );

    EngineBuilder resourceLoader( ResourceLoader resourceLoader );

    Engine build();

    public static EngineBuilder newBuilder()
    {
        return null;
    }
}
