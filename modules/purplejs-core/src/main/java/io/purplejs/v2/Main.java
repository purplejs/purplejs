package io.purplejs.v2;

import javax.inject.Inject;

import io.purplejs.v2.injector.Injector;
import io.purplejs.v2.injector.InjectorBuilder;

public class Main
{
    public static class MyObject
    {
        @Inject
        MyObject2 value;
    }

    public static class MyObject2
    {
    }

    public static void main( final String... args )
    {
        /*
        final EngineBuilder builder = EngineBuilder.newBuilder();

        for ( int i = 0; i < 100; i++ )
        {
            final int value = i;

            builder.module( new EngineModule()
            {
                @Override
                protected void configure()
                {
                    bind( Integer.class ).toInstance( value );
                }
            } );
        }

        final Engine engine = builder.build();

        System.out.println( engine.getRunMode() );
        System.out.println( engine.getClassLoader() );
        System.out.println( engine.getResourceLoader() );
        System.out.println( engine.getConfig() );
        System.out.println( engine.getGlobalVariables() );
        System.out.println( engine.provider( String.class ).get() );
        System.out.println( engine.provider( Integer.class ).get() );
        */

        final Injector injector = InjectorBuilder.newBuilder().
            provider( MyObject.class, MyObject::new ).
            instance( MyObject2.class, new MyObject2() ).
            build();

        System.out.println( injector.instance( MyObject.class ).value );
        System.out.println( injector.instance( MyObject.class ).value );
        System.out.println( injector.instance( MyObject.class ).value );

        Engine engine = null;
    }
}
