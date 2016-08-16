package io.purplejs.core.internal.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.purplejs.core.context.ScriptLogger;
import io.purplejs.core.resource.ResourcePath;

final class ScriptLoggerImpl
    implements ScriptLogger
{
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
        this.logger.debug( format( message, args ) );
    }

    @Override
    public void info( final String message, final Object... args )
    {
        this.logger.info( format( message, args ) );
    }

    @Override
    public void warning( final String message, final Object... args )
    {
        this.logger.warn( format( message, args ) );
    }

    @Override
    public void error( final String message, final Object... args )
    {
        this.logger.error( format( message, args ) );
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
        return LoggerFactory.getLogger( PREFIX + name );
    }
}
