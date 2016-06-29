package io.purplejs.v2.injector;

import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.collect.ImmutableMap;

final class InjectorImpl
    implements Injector
{
    private final ImmutableMap<Class, Binding> map;

    InjectorImpl( final ImmutableMap<Class, Binding> map )
    {
        this.map = map;
        initialize();
    }

    private void initialize()
    {
        for ( final Binding binding : this.map.values() )
        {
            binding.inject( this );
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Binding<T> bindingOrNull( final Class<T> type )
    {
        return (Binding<T>) this.map.get( type );
    }

    private <T> Binding<T> binding( final Class<T> type )
    {
        final Binding<T> binding = bindingOrNull( type );
        if ( binding != null )
        {
            return binding;
        }

        throw new IllegalArgumentException( String.format( "No binding of type [%s]", type.getName() ) );
    }

    @Override
    public <T> T instance( final Class<T> type )
    {
        return binding( type ).get();
    }

    @Override
    public <T> Provider<T> provider( final Class<T> type )
    {
        return binding( type );
    }

    @Override
    public void inject( final Object instance )
    {
        for ( final Field field : instance.getClass().getDeclaredFields() )
        {
            injectField( instance, field );
        }
    }

    private void injectField( final Object instance, final Field field )
    {
        if ( !field.isAnnotationPresent( Inject.class ) )
        {
            return;
        }

        final Class type = field.getType();
        final Binding<?> binding = bindingOrNull( type );

        if ( binding == null )
        {
            throw new IllegalArgumentException(
                String.format( "Could not inject [%s.%s] with type [%s]", field.getDeclaringClass().getName(), field.getName(),
                               type.getName() ) );
        }

        try
        {
            field.setAccessible( true );
            field.set( instance, binding.get() );
        }
        catch ( final Exception e )
        {
            throw new IllegalArgumentException(
                String.format( "Failed to inject [%s.%s] with type [%s]", field.getDeclaringClass().getName(), field.getName(),
                               type.getName() ), e );
        }
    }
}
