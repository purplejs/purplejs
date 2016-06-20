package io.purplejs.script;

import org.junit.Test;

import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

import static org.junit.Assert.*;

public class FinalizerTest
    extends AbstractScriptTest
{
    @Test
    public void resolve()
    {
        final ResourcePath script = ResourcePath.from( "/finalizer/finalizer-test.js" );
        final ScriptExports exports = run( script );

        assertNotNull( exports );
        assertEquals( script, exports.getResource() );
        assertEquals( false, exports.executeMethod( "isExecuted" ).getValue() );

        this.engine.dispose();
        assertEquals( true, exports.executeMethod( "isExecuted" ).getValue() );
    }
}
