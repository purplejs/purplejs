package org.purplejs.impl.value;

import org.purplejs.value.ScriptValue;

public interface ScriptValueFactory
{
    ScriptMethodInvoker getInvoker();

    ScriptValue newValue( Object value );
}
