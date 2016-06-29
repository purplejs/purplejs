package io.purplejs;

public interface EngineModule
{
    void init( Engine engine );

    void configure( EngineBuilder builder );

    void dispose();
}
