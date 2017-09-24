package io.purplejs.core.internal.executor;

/**
 * This logger is added as a global object (log) in the Javascript environment.
 */
public interface ScriptLogger
{
    /**
     * Log debug message.
     *
     * @param message Message to log. This can be a printf-like pattern.
     * @param args    Optional arguments.
     */
    void debug( String message, Object... args );

    /**
     * Log info message.
     *
     * @param message Message to log. This can be a printf-like pattern.
     * @param args    Optional arguments.
     */
    void info( String message, Object... args );

    /**
     * Log warning message.
     *
     * @param message Message to log. This can be a printf-like pattern.
     * @param args    Optional arguments.
     */
    void warning( String message, Object... args );

    /**
     * Log error message.
     *
     * @param message Message to log. This can be a printf-like pattern.
     * @param args    Optional arguments.
     */
    void error( String message, Object... args );
}
