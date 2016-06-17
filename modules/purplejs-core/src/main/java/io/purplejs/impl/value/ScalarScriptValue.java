package io.purplejs.impl.value;

import io.purplejs.impl.util.ConvertHelper;

final class ScalarScriptValue
    extends AbstractScriptValue
{
    private final Object value;

    public ScalarScriptValue( final Object value )
    {
        this.value = value;
    }

    @Override
    public boolean isValue()
    {
        return true;
    }

    @Override
    public Object getValue()
    {
        return this.value;
    }

    @Override
    public <T> T getValue( final Class<T> type )
    {
        return ConvertHelper.convert( this.value, type );
    }
}
