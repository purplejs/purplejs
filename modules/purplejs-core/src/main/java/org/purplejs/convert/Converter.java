package org.purplejs.convert;

public interface Converter<T>
{
    Class<T> getType();

    T convert( Object value );
}