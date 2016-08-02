package io.purplejs.script;

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
        final ResourcePath path = ResourcePath.from( "/resolve/resolve-test.js" );
        final ScriptExports exports = run( path );

        assertNotNull( exports );
        assertEquals( path, exports.getResource() );
    }
}
