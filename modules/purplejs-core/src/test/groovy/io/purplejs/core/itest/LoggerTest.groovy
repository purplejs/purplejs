package io.purplejs.core.itest

class LoggerTest
    extends AbstractIntegrationTest
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
