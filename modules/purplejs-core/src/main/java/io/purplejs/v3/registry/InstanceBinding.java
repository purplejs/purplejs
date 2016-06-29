package io.purplejs.v3.registry;

final class InstanceBinding<T>
    implements Binding<T>
{
    private T instance;

    private Injector injector;

    private boolean injected;

    InstanceBinding( final T instance )
    {
        this.instance = instance;
    }

    @Override
    public void init( final Injector injector )
    {
        this.injector = injector;
    }

    @Override
    public T get()
    {
        if ( !this.injected )
        {
            this.injector.inject( this.instance );
            this.injected = true;
        }

        return this.instance;
    }
}
