package org.purplejs.impl.script.value;

import org.purplejs.script.ScriptValue;

public interface ScriptValueFactory
{
    ScriptMethodInvoker getInvoker();

    ScriptValue newValue( Object value );
}
