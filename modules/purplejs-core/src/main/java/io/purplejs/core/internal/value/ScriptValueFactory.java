package io.purplejs.core.internal.value;

import io.purplejs.core.value.ScriptValue;

public interface ScriptValueFactory
{
    ScriptValue newValue( Object value );
}
