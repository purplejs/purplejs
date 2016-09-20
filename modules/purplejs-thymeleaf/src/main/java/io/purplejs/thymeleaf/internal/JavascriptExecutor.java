package io.purplejs.thymeleaf.internal;

import java.util.function.Function;

final class JavascriptExecutor
{
    public Object exec( final Object... args )
    {
        if ( args.length == 0 )
        {
            throw new IllegalArgumentException( "One or more arguments required" );
        }

        final Object func = args[0];
        if ( !( func instanceof Function ) )
        {
            throw new IllegalArgumentException( "First argument needs to be a function" );
        }

        return doExec( (Function) func, shiftArgs( args ) );
    }

    @SuppressWarnings("unchecked")
    private Object doExec( final Function func, final Object[] args )
    {
        return func.apply( args );
    }

    private Object[] shiftArgs( final Object[] args )
    {
        final Object[] result = new Object[args.length - 1];
        System.arraycopy( args, 1, result, 0, result.length );
        return result;
    }
}
