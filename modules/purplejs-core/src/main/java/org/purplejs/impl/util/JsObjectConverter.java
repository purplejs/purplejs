package org.purplejs.impl.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.purplejs.impl.json.ScriptJsonGenerator;
import org.purplejs.json.JsonSerializable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.NativeDate;

public final class JsObjectConverter
{
    public static Object toJs( final Object value )
    {
        if ( value instanceof JsonSerializable )
        {
            return toJs( (JsonSerializable) value );
        }

        if ( value instanceof List )
        {
            return toJs( (List) value );
        }

        return value;
    }

    public static Object[] toJsArray( final Object[] values )
    {
        final Object[] result = new Object[values.length];
        for ( int i = 0; i < values.length; i++ )
        {
            result[i] = toJs( values[i] );
        }

        return result;
    }

    private static Object toJs( final JsonSerializable value )
    {
        final ScriptJsonGenerator generator = new ScriptJsonGenerator();
        value.serialize( generator );
        return generator.getRoot();
    }

    private static Object toJs( final List list )
    {
        final Object array = NashornHelper.newNativeArray();
        for ( final Object element : list )
        {
            NashornHelper.addToNativeArray( array, toJs( element ) );
        }

        return array;
    }

    public static Object fromJs( final Object value )
    {
        return toObject( value );
    }

    private static Object toObject( final Object source )
    {
        final Object wrapped = NashornHelper.wrap( source );
        final Object unwrapped = NashornHelper.unwrap( source );

        if ( unwrapped instanceof NativeDate )
        {
            return toDate( (NativeDate) unwrapped );
        }

        if ( wrapped instanceof ScriptObjectMirror )
        {
            return toObject( (ScriptObjectMirror) wrapped );
        }

        return source;
    }

    private static Object toObject( final ScriptObjectMirror source )
    {
        if ( source.isArray() )
        {
            return toList( source );
        }
        else if ( source.isFunction() )
        {
            return toFunction( source );
        }
        else
        {
            return toMap( source );
        }
    }

    private static List<Object> toList( final ScriptObjectMirror source )
    {
        final List<Object> result = Lists.newArrayList();
        for ( final Object item : source.values() )
        {
            final Object converted = toObject( item );
            if ( converted != null )
            {
                result.add( converted );
            }
        }

        return result;
    }

    public static Map<String, Object> toMap( final Object source )
    {
        final Object object = NashornHelper.wrap( source );
        if ( object instanceof ScriptObjectMirror )
        {
            return toMap( (ScriptObjectMirror) object );
        }

        return Maps.newHashMap();
    }

    private static Map<String, Object> toMap( final ScriptObjectMirror source )
    {
        final Map<String, Object> result = Maps.newLinkedHashMap();
        for ( final Map.Entry<String, Object> entry : source.entrySet() )
        {
            final Object converted = toObject( entry.getValue() );
            if ( converted != null )
            {
                result.put( entry.getKey(), converted );
            }
        }

        return result;
    }

    private static Function<Object[], Object> toFunction( final ScriptObjectMirror source )
    {
        return arg -> toObject( source.call( null, arg ) );
    }

    private static Date toDate( final NativeDate date )
    {
        final long time = (long) NativeDate.getTime( date );
        final long tzOffsetMin = (long) NativeDate.getTimezoneOffset( date );
        return new Date( time + TimeUnit.MINUTES.toMillis( tzOffsetMin ) );
    }
}
