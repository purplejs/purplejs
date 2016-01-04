package org.purplejs.convert;

public interface ConvertService
{
    <S, T> T convert( S source, Class<T> toType );

    <S, T> T convertOrNull( S source, Class<T> toType );

    <S, T> T convertOrDefault( S source, Class<T> toType, T defValue );
}
