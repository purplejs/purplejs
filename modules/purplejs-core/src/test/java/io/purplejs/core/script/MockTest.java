package io.purplejs.core.script;

import org.junit.Test;

import io.purplejs.core.resource.ResourcePath;

public class MockTest
    extends AbstractScriptTest
{
    @Test
    public void testMock()
    {
        run( ResourcePath.from( "/app/mock/mock-test.js" ) );
    }
}
