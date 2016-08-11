package io.purplejs.impl.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import io.purplejs.impl.util.ConvertHelper;

final class ScalarScriptValue
    extends AbstractScriptValue
{
    private final Object value;

    ScalarScriptValue( final Object value )
    {
        this.value = value;
    }

    @Override
    public boolean isValue()
    {
        return true;
    }

    @Override
    public Object getValue()
    {
        return this.value;
    }

    @Override
    public <T> T getValue( final Class<T> type )
    {
        return ConvertHelper.INSTANCE.convert( this.value, type );
    }

    @Override
    public JsonElement toJson()
    {
        if ( this.value instanceof Boolean )
        {
            return new JsonPrimitive( (Boolean) this.value );
        }

        if ( this.value instanceof Number )
        {
            return new JsonPrimitive( (Number) this.value );
        }

        return new JsonPrimitive( this.value.toString() );
    }

    @Override
    public Object getRaw()
    {
        return this.value;
    }
}
