package io.purplejs.v3;


public class Main
{
    public static class MyObject
        implements EngineAware
    {
        @Override
        public void setEngine( final Engine engine )
        {
            System.out.println( "HERE!" + engine );
        }
    }

    public static void main( final String... args )
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        builder.module( binder -> {
            binder.instance( MyObject.class, new MyObject() );

            binder.initializer( engine -> {
                System.out.println( "Initialize " + engine );
            } );

            binder.disposer( engine -> {
                System.out.println( "Dispose " + engine );
            } );
        } );
        builder.module( new MyOtherModule() );

        final Engine engine = builder.build();

        System.out.println( engine.isDevMode() );
        System.out.println( engine.getClassLoader() );
        System.out.println( engine.getResourceLoader() );
        System.out.println( engine.getConfig() );

        System.out.println( engine.getInstance( MyObject.class ) );

        engine.dispose();

        // System.out.println( engine.provider( String.class ).get() );
        // System.out.println( engine.provider( Integer.class ).get() );
    }
}
