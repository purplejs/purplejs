package io.purplejs.script;

import io.purplejs.testing.ScriptTestSupport;

public class MyTest
    extends ScriptTestSupport
{
    public MyTest()
    {
        addTestFile( "/test/*-test.js" );
    }
}
