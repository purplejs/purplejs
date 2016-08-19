package io.purplejs.core.internal.executor

import io.purplejs.core.resource.ResourcePath
import org.slf4j.Logger
import spock.lang.Specification

class ScriptLoggerImplTest
    extends Specification
{
    def Logger logger;

    def ScriptLoggerImpl scriptLogger;

    def setup()
    {
        this.logger = Mock( Logger.class );
        this.scriptLogger = new ScriptLoggerImpl( this.logger );
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
        1 * this.logger.debug( "my message" );
    }

    def "info"()
    {
        when:
        this.scriptLogger.info( "my message" );

        then:
        1 * this.logger.info( "my message" );
    }

    def "warning"()
    {
        when:
        this.scriptLogger.warning( "my message" );

        then:
        1 * this.logger.warn( "my message" );
    }

    def "error"()
    {
        when:
        this.scriptLogger.error( "my message" );

        then:
        1 * this.logger.error( "my message" );
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
