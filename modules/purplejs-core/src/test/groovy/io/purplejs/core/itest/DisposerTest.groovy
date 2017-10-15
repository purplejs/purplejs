package io.purplejs.core.itest

class DisposerTest
    extends AbstractCoreITest
{
    def "run disposers"()
    {
        setup:
        file( '/test.js', '''
            var executed = false;

            $system.disposer(function () {
                executed = true;
            });

            t.assertEquals(false, executed);
            $system.getEngine().dispose();
            t.assertEquals(true, executed);
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }
}
