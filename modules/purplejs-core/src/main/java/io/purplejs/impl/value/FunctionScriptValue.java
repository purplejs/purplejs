package io.purplejs.impl.value;

import io.purplejs.impl.nashorn.NashornRuntime;
import io.purplejs.impl.util.ErrorHelper;
import io.purplejs.impl.util.JsObjectConverter;
import io.purplejs.value.ScriptValue;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

final class FunctionScriptValue
    extends AbstractScriptValue
{
    private final ScriptValueFactory factory;

    private final ScriptObjectMirror value;

    private final NashornRuntime runtime;

    FunctionScriptValue( final ScriptValueFactory factory, final ScriptObjectMirror value, final NashornRuntime runtime )
    {
        this.factory = factory;
        this.value = value;
        this.runtime = runtime;
    }

    @Override
    public boolean isFunction()
    {
        return true;
    }

    @Override
    public ScriptValue call( final Object... args )
    {
        try
        {
            final Object[] jsArray = new JsObjectConverter( this.runtime ).toJsArray( args );
            final Object result = this.value.call( null, jsArray );
            return this.factory.newValue( result );
        }
        catch ( final Exception e )
        {
            throw ErrorHelper.handleError( e );
        }
    }
}
