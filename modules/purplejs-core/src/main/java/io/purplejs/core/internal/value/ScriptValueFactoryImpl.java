package io.purplejs.core.internal.value;

import io.purplejs.core.internal.nashorn.NashornHelper;
import io.purplejs.core.internal.nashorn.NashornRuntime;
import io.purplejs.core.value.ScriptValue;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public final class ScriptValueFactoryImpl
    implements ScriptValueFactory
{
    private final NashornRuntime runtime;

    public ScriptValueFactoryImpl( final NashornRuntime runtime )
    {
        this.runtime = runtime;
    }

    @Override
    public ScriptValue newValue( final Object value )
    {
        if ( value == null )
        {
            return null;
        }

        if ( NashornHelper.isUndefined( value ) )
        {
            return null;
        }

        if ( NashornHelper.isDateType( value ) )
        {
            return new ScalarScriptValue( NashornHelper.toDate( value ) );
        }

        if ( value instanceof ScriptObjectMirror )
        {
            return newValueFromObjectMirror( (ScriptObjectMirror) value );
        }

        return new ScalarScriptValue( value );
    }

    private ScriptValue newValueFromObjectMirror( final ScriptObjectMirror value )
    {
        if ( value.isFunction() )
        {
            return new FunctionScriptValue( this, value, this.runtime );
        }

        if ( value.isArray() )
        {
            return new ArrayScriptValue( this, value );
        }

        return new ObjectScriptValue( this, value );
    }
}
