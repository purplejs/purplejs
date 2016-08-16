package io.purplejs.example;

import io.purplejs.testing.junit.ScriptTestSupport;

public class ExampleTest
    extends ScriptTestSupport
{
    public ExampleTest()
    {
        addTestFile( "/**/*-test.js" );
    }
}
