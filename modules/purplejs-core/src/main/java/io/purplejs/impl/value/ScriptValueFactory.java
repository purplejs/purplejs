package io.purplejs.impl.value;

import io.purplejs.value.ScriptValue;

public interface ScriptValueFactory
{
    ScriptValue newValue( Object value );
}
