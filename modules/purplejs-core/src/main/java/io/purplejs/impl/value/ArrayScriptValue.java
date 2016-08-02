package io.purplejs.impl.value;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import io.purplejs.value.ScriptValue;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

final class ArrayScriptValue
    extends AbstractScriptValue
{
    private final ScriptValueFactory factory;

    private final ScriptObjectMirror value;

    ArrayScriptValue( final ScriptValueFactory factory, final ScriptObjectMirror value )
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
    public JsonElement toJson()
    {
        final JsonArray json = new JsonArray();
        for ( final ScriptValue value : getArray() )
        {
            json.add( value.toJson() );
        }

        return json;
    }
}
