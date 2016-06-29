package io.purplejs.v3;

public class MyOtherModule
    implements EngineModule
{
    @Override
    public void configure( final EngineBinder binder )
    {
        binder.initializer( this::init );
        binder.disposer( this::dispose );
    }

    private void init( final Engine engine )
    {
        System.out.println( "Init " + this );
    }

    private void dispose( final Engine engine )
    {
        System.out.println( "Dispose " + this );
    }
}
