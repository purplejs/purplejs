package io.purplejs.script;

import org.junit.Test;

import io.purplejs.resource.ResourcePath;
import io.purplejs.testing.TestingSupport;

public class MockTest
    extends TestingSupport
{
    @Test
    public void resolve()
    {
        run( ResourcePath.from( "/mock/mock-test.js" ) );
    }
}
