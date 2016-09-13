package io.purplejs.core.exception;

import java.util.concurrent.Callable;

public final class ExceptionHelper
{
    /**
     * Rethrows a checked exception as unchecked exception. This method tricks the compiler into
     * thinking the exception is unchecked, rather than wrapping the given exception in a new
     * {@code RuntimeException}.
     * <p>
     * This method never returns. Nevertheless, it specifies a return type so it can be invoked as
     * {@code throw unchecked(e)} in contexts where an exception type is syntactically required
     * (e.g. when the enclosing method is non-void).
     *
     * @param e Throwable to be made unchecked.
     * @return This will never return anything. It's here to trick the compiler.
     */
    public static RuntimeException unchecked( final Throwable e )
    {
        ExceptionHelper.adapt( e );
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Exception> void adapt( final Throwable e )
        throws T
    {
        throw (T) e;
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
            throw ExceptionHelper.unchecked( e );
        }
    }
}
