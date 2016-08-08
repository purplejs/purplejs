package io.purplejs.script;

import io.purplejs.testing.TestingSupport;

public abstract class AbstractScriptTest
    extends TestingSupport
{
    AbstractScriptTest()
    {
        setClassLoaderPrefix( "/io/purplejs" );
    }
}
