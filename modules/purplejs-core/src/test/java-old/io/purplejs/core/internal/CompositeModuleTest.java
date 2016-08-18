package io.purplejs.core.internal;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineModule;

public class CompositeModuleTest
{
    private CompositeModule compositeModule;

    @Before
    public void setUp()
    {
        this.compositeModule = new CompositeModule();
    }

    @Test
    public void configure()
    {
        final EngineBinder binder = Mockito.mock( EngineBinder.class );
        final EngineModule module1 = Mockito.mock( EngineModule.class );
        final EngineModule module2 = Mockito.mock( EngineModule.class );

        this.compositeModule.add( module1 );
        this.compositeModule.add( module2 );

        this.compositeModule.configure( binder );

        Mockito.verify( module1, Mockito.times( 1 ) ).configure( binder );
        Mockito.verify( module2, Mockito.times( 1 ) ).configure( binder );
    }

    @Test
    public void init()
    {
        final Engine engine = Mockito.mock( Engine.class );
        final Consumer<Engine> initializer1 = engineMockCallback();
        final Consumer<Engine> initializer2 = engineMockCallback();

        this.compositeModule.addInitializer( initializer1 );
        this.compositeModule.addInitializer( initializer2 );

        this.compositeModule.init( engine );

        Mockito.verify( initializer1, Mockito.times( 1 ) ).accept( engine );
        Mockito.verify( initializer2, Mockito.times( 1 ) ).accept( engine );
    }

    @Test
    public void dispose()
    {
        final Engine engine = Mockito.mock( Engine.class );
        final Consumer<Engine> disposer1 = engineMockCallback();
        final Consumer<Engine> disposer2 = engineMockCallback();

        this.compositeModule.addDisposer( disposer1 );
        this.compositeModule.addDisposer( disposer2 );

        this.compositeModule.dispose( engine );

        Mockito.verify( disposer1, Mockito.times( 1 ) ).accept( engine );
        Mockito.verify( disposer2, Mockito.times( 1 ) ).accept( engine );
    }

    @SuppressWarnings("unchecked")
    private Consumer<Engine> engineMockCallback()
    {
        return Mockito.mock( Consumer.class );
    }
}
