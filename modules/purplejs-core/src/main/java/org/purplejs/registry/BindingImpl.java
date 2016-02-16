package org.purplejs.registry;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Supplier;

import com.google.common.collect.Iterators;

final class BindingImpl<T>
    implements Binding<T>
{
    private final Class<T> type;

    private final Supplier<T> supplier;

    private final Binding<T> next;

    public BindingImpl( final Class<T> type, final Supplier<T> supplier, final Binding<T> next )
    {
        this.type = type;
        this.supplier = supplier;
        this.next = next;
    }

    @Override
    public Class<T> getType()
    {
        return this.type;
    }

    @Override
    public T get()
    {
        if ( this.supplier == null )
        {
            throw new IllegalArgumentException( String.format( "Binding of type [%s] not found", this.type.getName() ) );
        }

        return this.supplier.get();
    }

    @Override
    public Iterator<T> iterator()
    {
        if ( this.supplier == null )
        {
            return Collections.emptyIterator();
        }

        final Iterator<T> singleton = Iterators.singletonIterator( get() );
        if ( this.next == null )
        {
            return singleton;
        }

        return Iterators.concat( singleton, this.next.iterator() );
    }

    @Override
    public boolean isEmpty()
    {
        return this.supplier == null;
    }

    public static <T> BindingImpl<T> empty( final Class<T> type )
    {
        return new BindingImpl<>( type, null, null );
    }
}
