package io.purplejs.inject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public final class InjectorImpl
    implements Injector
{
    @Override
    public void inject( final Object instance )
    {
        for ( final Method method : instance.getClass().getMethods() )
        {
            injectMethod( instance, method );
        }
    }

    private void injectMethod( final Object instance, final Method method )
    {
        if ( !isSetter( method ) )
        {
            return;
        }

        final Parameter parameter = method.getParameters()[0];
        final Class<?> type = parameter.getType();

        injectInstance( instance, method, type );
    }

    private boolean isSetter( final Method method )
    {
        if ( !Modifier.isPublic( method.getModifiers() ) )
        {
            return false;
        }

        if ( method.getParameters().length != 1 )
        {
            return false;
        }

        final String name = method.getName();
        return name.startsWith( "set" ) && name.length() > 3;
    }

    private void injectInstance( final Object instance, final Method method, final Class<?> type )
    {

    }
}
