package io.purplejs.v3.registry;

import io.purplejs.v3.Engine;
import io.purplejs.v3.EngineAware;

public final class AwareInjector
    implements Injector
{
    private final Engine engine;

    public AwareInjector( final Engine engine )
    {
        this.engine = engine;
    }

    @Override
    public void inject( final Object instance )
    {
        if ( instance instanceof EngineAware )
        {
            ( (EngineAware) instance ).setEngine( this.engine );
        }
    }
}
