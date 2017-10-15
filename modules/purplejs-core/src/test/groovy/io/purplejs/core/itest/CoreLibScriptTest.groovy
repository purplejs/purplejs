package io.purplejs.core.itest

class CoreLibScriptTest
    extends AbstractCoreITest
{
    def "newStream"()
    {
        setup:
        file( '/test.js', '''
            var core = require('/lib/core');
            
            var stream = core.newStream('hello');
            t.assertEquals(true, stream != undefined);
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }

    def "getStreamSize"()
    {
        setup:
        file( '/test.js', '''
            var core = require('/lib/core');

            var stream = core.newStream('hello');
            t.assertEquals(5, core.getStreamSize(stream));
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }

    def "readText"()
    {
        setup:
        file( '/test.js', '''
            var core = require('/lib/core');

            var stream = core.newStream('hello');
            t.assertEquals('hello', core.readText(stream));
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }

    def "readLines"()
    {
        setup:
        file( '/test.js', '''
            var core = require('/lib/core');

            var stream = core.newStream('1\\n2\\n3');
            t.assertEquals('["1","2","3"]', JSON.stringify(core.readLines(stream)));
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }

    def "processLines"()
    {
        setup:
        file( '/test.js', '''
            var core = require('/lib/core');

            var result = '';
            var stream = core.newStream('1\\n2\\n3');

            core.processLines(stream, function(line) {
                result = result + '-' + line;
            });

            t.assertEquals('-1-2-3', result);
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }

    def "loadResource"()
    {
        setup:
        file( '/a/b/other.txt', '''
            hello
        ''' );

        file( '/a/b/test.js', '''
            var core = require('/lib/core');

            var stream = core.loadResource($system.resolve('./other.txt'))
            var text = core.readText(stream);

            t.assertEquals('hello', text);
        ''' );

        when:
        def exports = run( '/a/b/test.js' );

        then:
        exports != null;
    }
}
