package io.purplejs.impl.value;

import io.purplejs.impl.nashorn.NashornHelper;
import io.purplejs.impl.nashorn.NashornRuntime;
import io.purplejs.value.ScriptValue;
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

        if ( NashornHelper.INSTANCE.isUndefined( value ) )
        {
            return null;
        }

        if ( NashornHelper.INSTANCE.isDateType( value ) )
        {
            return new ScalarScriptValue( NashornHelper.INSTANCE.toDate( value ) );
        }

        if ( value instanceof ScriptObjectMirror )
        {
            return newValue( (ScriptObjectMirror) value );
        }

        return new ScalarScriptValue( value );
    }

    private ScriptValue newValue( final ScriptObjectMirror value )
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
