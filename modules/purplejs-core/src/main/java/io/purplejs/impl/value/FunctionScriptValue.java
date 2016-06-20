package io.purplejs.impl.value;

import io.purplejs.value.ScriptValue;
import jdk.nashorn.api.scripting.JSObject;

final class FunctionScriptValue
    extends AbstractScriptValue
{
    private final ScriptValueFactory factory;

    private final JSObject value;

    FunctionScriptValue( final ScriptValueFactory factory, final JSObject value )
    {
        this.factory = factory;
        this.value = value;
    }

    @Override
    public boolean isFunction()
    {
        return true;
    }

    @Override
    public ScriptValue call( final Object... args )
    {
        final Object result = this.factory.getInvoker().invoke( this.value, args );
        return this.factory.newValue( result );
    }
}
