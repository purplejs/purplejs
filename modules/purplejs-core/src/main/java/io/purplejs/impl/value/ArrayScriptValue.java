package io.purplejs.impl.value;

import java.util.List;

import io.purplejs.value.ScriptValue;

import com.google.common.collect.Lists;

import jdk.nashorn.api.scripting.JSObject;

final class ArrayScriptValue
    extends AbstractScriptValue
{
    private final ScriptValueFactory factory;

    private final JSObject value;

    public ArrayScriptValue( final ScriptValueFactory factory, final JSObject value )
    {
        this.factory = factory;
        this.value = value;
    }

    @Override
    public boolean isArray()
    {
        return true;
    }

    @Override
    public List<ScriptValue> getArray()
    {
        final List<ScriptValue> result = Lists.newArrayList();
        for ( final Object item : this.value.values() )
        {
            final ScriptValue wrapped = this.factory.newValue( item );
            if ( wrapped != null )
            {
                result.add( wrapped );
            }
        }

        return result;
    }

    @Override
    public <T> List<T> getArray( final Class<T> type )
    {
        final List<T> result = Lists.newArrayList();
        for ( final ScriptValue item : getArray() )
        {
            final T converted = item.getValue( type );
            if ( converted != null )
            {
                result.add( converted );
            }
        }

        return result;
    }
}
