package io.purplejs.core.script;

import org.junit.Test;

import io.purplejs.core.resource.ResourcePath;

public class MockTest
    extends AbstractScriptTest
{
    @Test
    public void testMock()
    {
        run( ResourcePath.from( "/mock/mock-test.js" ) );
    }
}
