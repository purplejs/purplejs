package io.purplejs.v2;

import java.io.File;

import io.purplejs.resource.ResourceLoader;
import io.purplejs.v2.impl.EngineBuilderImpl;

public interface EngineBuilder
{
    EngineBuilder mode( RunMode mode );

    EngineBuilder module( EngineModule module );

    EngineBuilder devMode( boolean devMode );

    EngineBuilder devSourceDir( File dir );

    EngineBuilder classLoader( ClassLoader classLoader );

    EngineBuilder resourceLoader( ResourceLoader resourceLoader );

    EngineBuilder globalVariable( String name, Object value );

    EngineBuilder config( String name, String value );

    Engine build();

    public static EngineBuilder newBuilder()
    {
        return new EngineBuilderImpl();
    }
}
