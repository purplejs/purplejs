package io.purplejs.core.script

import io.purplejs.core.ScriptTestSupport

class DisposerTest
    extends ScriptTestSupport
{
    def "run disposers"()
    {
        setup:
        file( '/test.js', '''
            var executed = false;

            __.disposer(function () {
                executed = true;
            });

            t.assertEquals(false, executed);
            __.engine.dispose();
            t.assertEquals(true, executed);
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }
}
