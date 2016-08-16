package io.purplejs.core.context;

public interface ScriptLogger
{
    void debug( String message, Object... args );

    void info( String message, Object... args );

    void warning( String message, Object... args );

    void error( String message, Object... args );
}
