package io.purplejs.impl.value;

import io.purplejs.value.ScriptValue;

public interface ScriptValueFactory
{
    ScriptMethodInvoker getInvoker();

    ScriptValue newValue( Object value );
}
