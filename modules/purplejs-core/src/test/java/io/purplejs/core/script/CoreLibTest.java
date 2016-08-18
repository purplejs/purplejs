package io.purplejs.core.script;

import org.junit.Test;

public class CoreLibTest
    extends AbstractScriptTest
{
    @Test
    public void testNewStream()
    {
        run( "/app/core-test.js", "testNewStream" );
    }

    @Test
    public void testStreamSize()
    {
        run( "/app/core-test.js", "testStreamSize" );
    }

    @Test
    public void testReadText()
    {
        run( "/app/core-test.js", "testReadText" );
    }

    @Test
    public void testReadLines()
    {
        run( "/app/core-test.js", "testReadLines" );
    }
}

