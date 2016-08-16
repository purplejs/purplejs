package io.purplejs.core.script;

import org.junit.Test;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;

import static org.junit.Assert.*;

public class LoggerTest
    extends AbstractScriptTest
{
    @Test
    public void log()
    {
        final ResourcePath script = ResourcePath.from( "/logger/logger.js" );
        final ScriptExports exports = run( script );
        assertNotNull( exports );
    }
}
