package io.purplejs.core.script

import io.purplejs.core.ScriptTestSupport

class LoggerTest
    extends ScriptTestSupport
{
    def "log levels"()
    {
        setup:
        file( '/test.js', '''
            log.debug('debug message');
            log.info('%s message', 'info');
            log.warning('%s %s', 'warning', 'message');
            log.error('error %s', 'message');
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }
}
