package io.purplejs.core.internal.executor

import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

class ScriptLoggerImplTest
    extends Specification
{
    def LogRecord record;

    def ScriptLoggerImpl scriptLogger;

    def setup()
    {
        def logger = Logger.getLogger( 'unique.logger.' + UUID.randomUUID().toString() );
        logger.level = Level.FINEST;

        logger.addHandler( new Handler() {
            @Override
            void publish( final LogRecord record )
            {
                ScriptLoggerImplTest.this.record = record;
            }

            @Override
            void flush()
            {}

            @Override
            void close()
                throws SecurityException
            {}
        } );

        this.scriptLogger = new ScriptLoggerImpl( logger );
    }

    def "pathToLogger"()
    {
        when:
        def logger = ScriptLoggerImpl.pathToLogger( ResourcePath.from( "/a/b/c.js" ) );

        then:
        logger.getName() == 'js.a.b.c';
    }

    def "debug"()
    {
        when:
        this.scriptLogger.debug( "my message" );

        then:
        this.record.level == Level.FINEST;
        this.record.message == 'my message';
    }

    def "info"()
    {
        when:
        this.scriptLogger.info( "my message" );

        then:
        this.record.level == Level.INFO;
        this.record.message == 'my message';
    }

    def "warning"()
    {
        when:
        this.scriptLogger.warning( "my message" );

        then:
        this.record.level == Level.WARNING;
        this.record.message == 'my message';
    }

    def "error"()
    {
        when:
        this.scriptLogger.error( "my message" );

        then:
        this.record.level == Level.SEVERE;
        this.record.message == 'my message';
    }

    def "format"()
    {
        when:
        def message = ScriptLoggerImpl.format( "my message" );

        then:
        message == 'my message';

        when:
        message = ScriptLoggerImpl.format( "%s %s", "my", "message" );

        then:
        message == 'my message';
    }
}
