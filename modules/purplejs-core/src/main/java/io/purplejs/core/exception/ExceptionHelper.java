package io.purplejs.core.exception;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.Callable;

public final class ExceptionHelper
{
    /**
     * Converts an exception to a runtime exception.
     *
     * @param e Exception to convert.
     * @return a RuntimeException.
     */
    public static RuntimeException toRuntime( final Exception e )
    {
        if ( e instanceof RuntimeException )
        {
            return (RuntimeException) e;
        }

        return new UndeclaredThrowableException( e );
    }

    /**
     * Tries to call the callable and return the result. If an exception occurs, it will create unchecked
     * exceptions for checked exceptions.
     *
     * @param exec Callable to execute.
     * @param <T>  Type of return parameter.
     * @return result from callable or throws exception.
     */
    public static <T> T wrap( final Callable<T> exec )
    {
        try
        {
            return exec.call();
        }
        catch ( final RuntimeException e )
        {
            throw e;
        }
        catch ( final Exception e )
        {
            throw toRuntime( e );
        }
    }
}
