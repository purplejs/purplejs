package io.purplejs.core.internal.value;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

import io.purplejs.core.value.ScriptValue;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

final class ObjectScriptValue
    extends AbstractScriptValue
{
    private final ScriptValueFactory factory;

    private final ScriptObjectMirror value;

    ObjectScriptValue( final ScriptValueFactory factory, final ScriptObjectMirror value )
    {
        this.factory = factory;
        this.value = value;
    }

    @Override
    public boolean isObject()
    {
        return true;
    }

    @Override
    public Set<String> getKeys()
    {
        return this.value.keySet();
    }

    @Override
    public boolean hasMember( final String key )
    {
        return this.value.hasMember( key );
    }

    @Override
    public ScriptValue getMember( final String key )
    {
        return this.factory.newValue( this.value.getMember( key ) );
    }

    @Override
    public Map<String, ScriptValue> getMap()
    {
        final Map<String, ScriptValue> map = Maps.newHashMap();
        for ( final String key : getKeys() )
        {
            map.put( key, getMember( key ) );
        }

        return map;
    }

    @Override
    public Object toJavaObject()
    {
        final Map<String, Object> map = Maps.newHashMap();
        for ( final Map.Entry<String, ScriptValue> entry : getMap().entrySet() )
        {
            map.put( entry.getKey(), entry.getValue().toJavaObject() );
        }

        return map;
    }

    @Override
    public Object getRaw()
    {
        return this.value;
    }
}
