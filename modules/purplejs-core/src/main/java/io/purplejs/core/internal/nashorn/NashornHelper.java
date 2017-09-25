package io.purplejs.core.internal.nashorn;

import java.util.Date;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ScriptRuntime;

public final class NashornHelper
{
    public static boolean isUndefined( final Object value )
    {
        return ( value == null ) || ( value == ScriptRuntime.UNDEFINED );
    }

    public static boolean isDateType( final Object value )
    {
        return ( value instanceof ScriptObjectMirror ) && isDateType( (ScriptObjectMirror) value );
    }

    private static boolean isDateType( final ScriptObjectMirror value )
    {
        return "Date".equalsIgnoreCase( value.getClassName() );
    }

    public static Date toDate( final Object value )
    {
        final Number time = (Number) ( (ScriptObjectMirror) value ).callMember( "getTime" );
        return new Date( time.longValue() );
    }

    public static boolean isArrayType( final Object value )
    {
        return ( value instanceof ScriptObjectMirror ) && ( (ScriptObjectMirror) value ).isArray();
    }

    public static boolean isObjectType( final Object value )
    {
        return ( value instanceof ScriptObjectMirror ) && !isArrayType( value );
    }

    public static void addToObject( final Object object, final String key, final Object value )
    {
        ( (ScriptObjectMirror) object ).put( key, value );
    }

    public static void addToArray( final Object array, final Object value )
    {
        ( (ScriptObjectMirror) array ).callMember( "push", value );
    }
}
