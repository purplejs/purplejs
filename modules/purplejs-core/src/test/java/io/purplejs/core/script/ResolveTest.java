package io.purplejs.core.script;

import org.junit.Test;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;

import static org.junit.Assert.*;

public class ResolveTest
    extends AbstractScriptTest
{
    @Test
    public void resolve()
    {
        final ResourcePath path = ResourcePath.from( "/app/resolve/resolve-test.js" );
        final ScriptExports exports = run( path );

        assertNotNull( exports );
        assertEquals( path, exports.getResource() );
    }
}
