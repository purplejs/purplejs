package io.purplejs.script;

import org.junit.Test;

import io.purplejs.EngineBinder;
import io.purplejs.resource.ResourcePath;
import io.purplejs.testing.TestingSupport;

public class GlobalTest
    extends TestingSupport
{
    @Override
    public void configure( final EngineBinder binder )
    {
        super.configure( binder );
        binder.globalVariable( "globalVar", "hello" );
    }

    @Test
    public void testScope()
    {
        run( ResourcePath.from( "/global/scope-test.js" ) );
    }

    @Test
    public void testVariable()
    {
        run( ResourcePath.from( "/global/variable-test.js" ) );
    }
}
