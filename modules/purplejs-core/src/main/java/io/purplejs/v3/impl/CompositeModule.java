package io.purplejs.v3.impl;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import io.purplejs.v3.Engine;
import io.purplejs.v3.EngineBinder;
import io.purplejs.v3.EngineModule;

final class CompositeModule
    implements EngineModule
{
    private final List<EngineModule> list;

    private final List<Consumer<Engine>> initializerList;

    private final List<Consumer<Engine>> disposerList;

    CompositeModule()
    {
        this.list = Lists.newArrayList();
        this.initializerList = Lists.newArrayList();
        this.disposerList = Lists.newArrayList();

        ServiceLoader.load( EngineModule.class ).forEach( this::add );
    }

    void add( final EngineModule module )
    {
        this.list.add( module );
    }

    public void init( final Engine engine )
    {
        this.initializerList.forEach( initializer -> init( initializer, engine ) );
    }

    private void init( final Consumer<Engine> initializer, final Engine engine )
    {
        initializer.accept( engine );
    }

    public void dispose( final Engine engine )
    {
        this.disposerList.forEach( disposer -> dispose( disposer, engine ) );
    }

    private void dispose( final Consumer<Engine> disposer, final Engine engine )
    {
        disposer.accept( engine );
    }

    @Override
    public void configure( final EngineBinder binder )
    {
        this.list.forEach( module -> module.configure( binder ) );
    }

    void addInitializer( final Consumer<Engine> initializer )
    {
        this.initializerList.add( initializer );
    }

    void addDisposer( final Consumer<Engine> disposer )
    {
        this.disposerList.add( disposer );
    }
}
