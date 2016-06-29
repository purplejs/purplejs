package io.purplejs.v3.registry;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

final class Bindings
{
    private final Map<Class, Binding> map;

    Bindings()
    {
        this.map = Maps.newHashMap();
    }

    @SuppressWarnings("unchecked")
    <T> Binding<T> getBindingOrNull( final Class<T> type )
    {
        return (Binding<T>) this.map.get( type );
    }

    <T> Binding<T> getBinding( final Class<T> type )
    {
        final Binding<T> binding = getBindingOrNull( type );
        if ( binding != null )
        {
            return binding;
        }

        throw new IllegalArgumentException( String.format( "No binding of type [%s]", type.getName() ) );
    }

    private <T> void add( final Class<T> type, final Binding<T> binding )
    {
        this.map.put( type, binding );
    }

    <T> void instance( final Class<T> type, final T instance )
    {
        add( type, new InstanceBinding<>( instance ) );
    }

    <T> void supplier( final Class<T> type, final Supplier<T> supplier )
    {
        add( type, new SupplierBinding<>( supplier ) );
    }

    void init( final Injector injector )
    {
        this.map.values().forEach( b -> b.init( injector ) );
    }
}
