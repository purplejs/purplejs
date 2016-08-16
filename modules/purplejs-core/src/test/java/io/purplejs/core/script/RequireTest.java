package io.purplejs.core.script;

import org.junit.Test;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;

import static org.junit.Assert.*;

public class RequireTest
    extends AbstractScriptTest
{
    @Test
    public void require()
    {
        final ResourcePath script = ResourcePath.from( "/require/require-test.js" );
        final ScriptExports exports = run( script );
        assertNotNull( exports );
    }

    @Test
    public void require_3rd()
    {
        final ResourcePath script = ResourcePath.from( "/require/3rd/require-3rd-test.js" );
        final ScriptExports exports = run( script );
        assertNotNull( exports );
    }
}
