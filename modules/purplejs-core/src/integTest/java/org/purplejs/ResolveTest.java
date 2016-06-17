package org.purplejs;

import org.junit.Test;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

import static org.junit.Assert.*;

public class ResolveTest
    extends AbstractScriptTest
{
    @Test
    public void resolve()
    {
        final ResourcePath script = ResourcePath.from( "/resolve/resolve-test.js" );
        final ScriptExports exports = run( script );

        assertNotNull( exports );
        assertEquals( script, exports.getResource() );
    }
}
