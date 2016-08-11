package io.purplejs.exception;

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
}
