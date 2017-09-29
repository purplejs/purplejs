package io.purplejs.core.itest

class ResolveTest
    extends AbstractCoreITest
{
    def "resolve relative"()
    {
        setup:
        file( '/a/b/test.js', '''
            var system = require('/lib/system');
            var path = system.resolve('./test.html').toString();
            t.assertEquals('/a/b/test.html', path);
        ''' );

        when:
        def exports = run( '/a/b/test.js' );

        then:
        exports != null;
    }

    def "resolve absolute"()
    {
        setup:
        file( '/a/b/test.js', '''
            var system = require('/lib/system');
            var path = system.resolve('/c/test.html').toString();
            t.assertEquals('/c/test.html', path);
        ''' );

        when:
        def exports = run( '/a/b/test.js' );

        then:
        exports != null;
    }

    def "resolve in require"()
    {
        setup:
        file( '/a/b/other.js', '''
            var system = require('/lib/system');
            var path = system.resolve('./test.html').toString();
            t.assertEquals('/a/b/test.html', path);
        ''' );

        file( '/test.js', '''
            require('/a/b/other.js');
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }
}
