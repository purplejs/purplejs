package io.purplejs.v2;

public interface EngineModule2
{
    void init( Engine engine );

    void dispose();

    void configure( EngineBinder binder );
}
