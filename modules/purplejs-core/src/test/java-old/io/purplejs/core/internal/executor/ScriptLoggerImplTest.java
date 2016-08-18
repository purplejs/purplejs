package io.purplejs.core.internal.executor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import io.purplejs.core.resource.ResourcePath;

import static org.junit.Assert.*;

public class ScriptLoggerImplTest
{
    private Logger logger;

    private ScriptLoggerImpl scriptLogger;

    @Before
    public void setUp()
    {
        this.logger = Mockito.mock( Logger.class );
        this.scriptLogger = new ScriptLoggerImpl( this.logger );
    }

    @Test
    public void pathToLogger()
    {
        final Logger logger = ScriptLoggerImpl.pathToLogger( ResourcePath.from( "/a/b/c.js" ) );
        assertEquals( "js.a.b.c", logger.getName() );
    }

    @Test
    public void debug()
    {
        this.scriptLogger.debug( "my message" );
        Mockito.verify( this.logger, Mockito.times( 1 ) ).debug( "my message" );
    }

    @Test
    public void info()
    {
        this.scriptLogger.info( "my message" );
        Mockito.verify( this.logger, Mockito.times( 1 ) ).info( "my message" );
    }

    @Test
    public void warning()
    {
        this.scriptLogger.warning( "my message" );
        Mockito.verify( this.logger, Mockito.times( 1 ) ).warn( "my message" );
    }

    @Test
    public void error()
    {
        this.scriptLogger.error( "my message" );
        Mockito.verify( this.logger, Mockito.times( 1 ) ).error( "my message" );
    }

    @Test
    public void format()
    {
        final String message1 = ScriptLoggerImpl.format( "my message" );
        assertEquals( "my message", message1 );

        final String message2 = ScriptLoggerImpl.format( "%s %s", "my", "message" );
        assertEquals( "my message", message2 );
    }
}
