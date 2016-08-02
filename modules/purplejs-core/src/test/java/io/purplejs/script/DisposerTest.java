package io.purplejs.script;

import org.junit.Test;

import io.purplejs.resource.ResourcePath;
import io.purplejs.testing.TestingSupport;
import io.purplejs.value.ScriptExports;

import static org.junit.Assert.*;

public class DisposerTest
    extends TestingSupport
{
    @Test
    public void resolve()
    {
        this.runDisposer = false;

        final ResourcePath path = ResourcePath.from( "/disposer/disposer-test.js" );
        final ScriptExports exports = run( path );

        assertNotNull( exports );
        assertEquals( path, exports.getResource() );
        assertEquals( false, exports.executeMethod( "isExecuted" ).getValue() );

        this.engine.dispose();
        assertEquals( true, exports.executeMethod( "isExecuted" ).getValue() );
    }
}
