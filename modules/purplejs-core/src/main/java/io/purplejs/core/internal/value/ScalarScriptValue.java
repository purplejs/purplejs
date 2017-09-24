package io.purplejs.core.internal.value;

import io.purplejs.core.internal.util.ConvertHelper;

final class ScalarScriptValue
    extends AbstractScriptValue
{
    private final Object value;

    ScalarScriptValue( final Object value )
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
        return ConvertHelper.INSTANCE.convert( this.value, type );
    }

    @Override
    public Object toJavaObject()
    {
        return this.value;
    }

    @Override
    public Object getRaw()
    {
        return this.value;
    }
}
