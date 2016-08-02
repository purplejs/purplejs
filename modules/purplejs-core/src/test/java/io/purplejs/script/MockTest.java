package io.purplejs.script;

import org.junit.Test;

import io.purplejs.resource.ResourcePath;

public class MockTest
    extends AbstractScriptTest
{
    @Test
    public void testMock()
    {
        run( ResourcePath.from( "/mock/mock-test.js" ) );
    }
}
