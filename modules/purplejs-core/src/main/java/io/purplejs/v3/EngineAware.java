package io.purplejs.v3;

// TODO: Probably not needed. It's only needed for Script beans, but they could use javascript to inject.
public interface EngineAware
{
    void setEngine( Engine engine );
}
