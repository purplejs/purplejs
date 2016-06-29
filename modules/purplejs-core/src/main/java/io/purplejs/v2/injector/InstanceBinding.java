package io.purplejs.v2.injector;

final class InstanceBinding<T>
    implements Binding<T>
{
    private T instance;

    InstanceBinding( final T instance )
    {
        this.instance = instance;
    }

    @Override
    public void inject( final Injector injector )
    {
        injector.inject( this.instance );
    }

    @Override
    public T get()
    {
        return this.instance;
    }
}
