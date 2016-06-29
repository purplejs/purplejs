package io.purplejs.v2.injector;

import javax.inject.Provider;

final class ProviderBinding<T>
    implements Binding<T>
{
    private final Provider<T> provider;

    private Injector injector;

    ProviderBinding( final Provider<T> provider )
    {
        this.provider = provider;
    }

    @Override
    public void inject( final Injector injector )
    {
        this.injector = injector;
    }

    @Override
    public T get()
    {
        final T instance = this.provider.get();
        if ( instance != null )
        {
            this.injector.inject( instance );
        }

        return instance;
    }
}
