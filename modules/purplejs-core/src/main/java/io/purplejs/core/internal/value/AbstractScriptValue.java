package io.purplejs.core.internal.value;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import io.purplejs.core.value.ScriptValue;

abstract class AbstractScriptValue
    implements ScriptValue
{
    @Override
    public boolean isArray()
    {
        return false;
    }

    @Override
    public boolean isObject()
    {
        return false;
    }

    @Override
    public boolean isValue()
    {
        return false;
    }

    @Override
    public boolean isFunction()
    {
        return false;
    }

    @Override
    public Object getValue()
    {
        return null;
    }

    @Override
    public <T> T getValue( final Class<T> type )
    {
        return null;
    }

    @Override
    public Set<String> getKeys()
    {
        return Sets.newHashSet();
    }

    @Override
    public boolean hasMember( final String key )
    {
        return false;
    }

    @Override
    public ScriptValue getMember( final String key )
    {
        return null;
    }

    @Override
    public List<ScriptValue> getArray()
    {
        return Collections.emptyList();
    }

    @Override
    public Map<String, ScriptValue> getMap()
    {
        return Collections.emptyMap();
    }

    @Override
    public ScriptValue call( final Object... args )
    {
        return null;
    }
}
