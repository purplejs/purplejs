package io.purplejs.core.script;

import org.junit.Test;

public class LoggerTest
    extends AbstractScriptTest
{
    @Test
    public void testLogger()
    {
        run( "/app/logger-test.js", "testLogger" );
    }
}
