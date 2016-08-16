package io.purplejs.core;

import java.io.File;

import io.purplejs.core.internal.EngineBuilderImpl;
import io.purplejs.core.resource.ResourceLoader;

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
