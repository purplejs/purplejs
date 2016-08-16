package io.purplejs.core.internal.executor;

import org.junit.Test;
import org.mockito.Mockito;

import io.purplejs.core.RunMode;
import io.purplejs.core.exception.NotFoundException;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.mock.MockResource;
import io.purplejs.core.value.ScriptExports;

import static org.junit.Assert.*;

public class ScriptExecutorImplTest
    extends AbstractExecutorTest
{
    @Override
    protected void doConfigure()
    {
        // Do nothing
    }

    @Test(expected = NotFoundException.class)
    public void resourceNotFound()
    {
        this.executor.executeMain( ResourcePath.from( "/a.js" ) );
    }

    @Test
    public void dispose()
    {
        final Runnable runnable1 = Mockito.mock( Runnable.class );
        final Runnable runnable2 = Mockito.mock( Runnable.class );

        this.executor.registerDisposer( ResourcePath.from( "/a.js" ), runnable1 );
        this.executor.registerDisposer( ResourcePath.from( "/b.js" ), runnable2 );

        this.executor.dispose();

        Mockito.verify( runnable1, Mockito.times( 1 ) ).run();
        Mockito.verify( runnable2, Mockito.times( 1 ) ).run();
    }

    @Test
    public void cache_prodMode()
    {
        runWithMode( RunMode.PROD, () -> testCache( false ) );
    }

    @Test
    public void cache_devMode()
    {
        runWithMode( RunMode.DEV, () -> testCache( true ) );
    }

    private void testCache( final boolean shoudRefresh )
    {
        final Runnable disposer = Mockito.mock( Runnable.class );

        final ResourcePath path = ResourcePath.from( "/a.js" );
        this.executor.registerDisposer( path, disposer );
        final MockResource resource = addResource( path.toString(), "module.exports = {};" );

        final ScriptExports exports1 = this.executor.executeMain( path );
        Mockito.verify( disposer, Mockito.times( 0 ) ).run();

        final ScriptExports exports2 = this.executor.executeMain( path );
        assertSame( exports1.getValue().getRaw(), exports2.getValue().getRaw() );
        Mockito.verify( disposer, Mockito.times( 0 ) ).run();

        resource.setLastModified( System.currentTimeMillis() + 1000 );

        final ScriptExports exports3 = this.executor.executeMain( path );

        if ( shoudRefresh )
        {
            assertNotSame( exports2.getValue().getRaw(), exports3.getValue().getRaw() );
            Mockito.verify( disposer, Mockito.times( 1 ) ).run();
        }
        else
        {
            assertSame( exports2.getValue().getRaw(), exports3.getValue().getRaw() );
            Mockito.verify( disposer, Mockito.times( 0 ) ).run();
        }
    }

    private void runWithMode( final RunMode mode, final Runnable runnable )
    {
        final RunMode old = RunMode.get();

        try
        {
            mode.set();
            runnable.run();
        }
        finally
        {
            old.set();
        }
    }
}
