package io.purplejs.core.internal.executor

import io.purplejs.core.RunMode
import io.purplejs.core.exception.NotFoundException
import io.purplejs.core.resource.ResourcePath

class ScriptExecutorImplTest
    extends AbstractExecutorTest
{
    @Override
    protected void doConfigure()
    {
        // Do nothing
    }

    def "resourceNotFound"()
    {
        when:
        this.executor.executeMain( ResourcePath.from( '/a.js' ) );

        then:
        thrown NotFoundException;
    }

    def "dispose"()
    {
        setup:
        def runnable1 = Mock( Runnable.class );
        def runnable2 = Mock( Runnable.class );

        this.executor.registerDisposer( ResourcePath.from( "/a.js" ), runnable1 );
        this.executor.registerDisposer( ResourcePath.from( "/b.js" ), runnable2 );

        when:
        this.executor.dispose();

        then:
        1 * runnable1.run();
        1 * runnable2.run();
    }

    def "cache test"()
    {
        setup:
        def oldMode = RunMode.get();
        mode.set();

        def disposer = Mock( Runnable.class );
        def path = ResourcePath.from( "/a.js" );

        this.executor.registerDisposer( path, disposer );
        def resource = addResource( path.toString(), "module.exports = {};" );

        when:
        def exports1 = this.executor.executeMain( path );

        then:
        0 * disposer.run();

        when:
        def exports2 = this.executor.executeMain( path );
        exports1.value.raw == exports2.value.raw;

        then:
        0 * disposer.run();

        when:
        resource.lastModified = System.currentTimeMillis() + 3000;
        def exports3 = this.executor.executeMain( path );

        then:
        ( exports2.value.raw.hashCode() == exports3.value.raw.hashCode() ) != refresh;
        ( refresh ? 1 : 0 ) * disposer.run();

        cleanup:
        oldMode.set();

        where:
        mode         | refresh
        RunMode.DEV  | true
        RunMode.PROD | false
    }
}
