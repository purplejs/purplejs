package io.purplejs;

import java.io.File;

import io.purplejs.impl.EngineBuilderImpl;
import io.purplejs.resource.ResourceLoader;

public interface EngineBuilder
{
    EngineBuilder devSourceDir( File dir );

    EngineBuilder classLoader( ClassLoader classLoader );

    EngineBuilder resourceLoader( ResourceLoader resourceLoader );

    EngineBuilder module( EngineModule module );

    Engine build();

    static EngineBuilder newBuilder()
    {
        return new EngineBuilderImpl();
    }
}
