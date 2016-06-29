package io.purplejs.impl.executor;

import javax.script.Bindings;

import io.purplejs.impl.util.JsObjectConverter;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.JSObject;

final class CallFunction
    extends AbstractJSObject
{
    final static String NAME = "__call";

    @Override
    public final boolean isFunction()
    {
        return true;
    }

    @Override
    public Object call( final Object thiz, final Object... args )
    {
        final JSObject func = (JSObject) args[0];
        final Object[] array = (Object[]) args[1];

        final Object[] jsArray = JsObjectConverter.toJsArray( array );
        return func.call( thiz, jsArray );
    }

    final void register( final Bindings bindings )
    {
        bindings.put( NAME, this );
    }
}
