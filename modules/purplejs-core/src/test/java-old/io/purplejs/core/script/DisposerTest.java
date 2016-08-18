package io.purplejs.core.script;

import org.junit.Test;

public class DisposerTest
    extends AbstractScriptTest
{
    @Test
    public void testDispose()
    {
        run( "/app/disposer-test.js", "testDispose" );
    }
}
