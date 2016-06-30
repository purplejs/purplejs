package io.purplejs.impl;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import io.purplejs.Engine;
import io.purplejs.EngineBinder;
import io.purplejs.EngineModule;

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
    }

    void autoLoad()
    {
        ServiceLoader.load( EngineModule.class ).forEach( this::add );
    }

    void add( final EngineModule module )
    {
        this.list.add( module );
    }

    void init( final Engine engine )
    {
        this.initializerList.forEach( initializer -> init( initializer, engine ) );
    }

    private void init( final Consumer<Engine> initializer, final Engine engine )
    {
        initializer.accept( engine );
    }

    void dispose( final Engine engine )
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
