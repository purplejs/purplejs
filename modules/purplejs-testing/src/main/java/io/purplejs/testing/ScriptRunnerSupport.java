package io.purplejs.testing;

import org.junit.runner.RunWith;

import io.purplejs.resource.ResourcePath;

@RunWith(ScriptTestRunner.class)
public abstract class ScriptRunnerSupport
    extends ScriptTestSupport
{
    abstract ResourcePath getResource();
}
