package io.purplejs.core.internal.executor;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.purplejs.core.context.ScriptLogger;
import io.purplejs.core.resource.ResourcePath;

final class ScriptLoggerImpl
    implements ScriptLogger
{
    private final static Level DEBUG = Level.FINEST;

    private final static Level ERROR = Level.SEVERE;

    private final static String PREFIX = "js";

    private final Logger logger;

    ScriptLoggerImpl( final ResourcePath path )
    {
        this( pathToLogger( path ) );
    }

    ScriptLoggerImpl( final Logger logger )
    {
        this.logger = logger;
    }

    @Override
    public void debug( final String message, final Object... args )
    {
        log( DEBUG, message, args );
    }

    @Override
    public void info( final String message, final Object... args )
    {
        log( Level.INFO, message, args );
    }

    @Override
    public void warning( final String message, final Object... args )
    {
        log( Level.WARNING, message, args );
    }

    @Override
    public void error( final String message, final Object... args )
    {
        log( ERROR, message, args );
    }

    private void log( final Level level, final String message, final Object... args )
    {
        this.logger.log( level, format( message, args ) );
    }

    static String format( final String message, final Object... args )
    {
        if ( args.length > 0 )
        {
            return String.format( message, args );
        }

        return message;
    }

    static Logger pathToLogger( final ResourcePath path )
    {
        final String name = path.toString().replaceAll( "(.+)\\..+", "$1" ).replace( '/', '.' );
        return Logger.getLogger( PREFIX + name );
    }
}
