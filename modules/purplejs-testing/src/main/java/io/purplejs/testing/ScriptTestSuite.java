package io.purplejs.testing;

import io.purplejs.core.EngineBuilder;
import io.purplejs.core.EngineModule;

public interface ScriptTestSuite
    extends EngineModule
{
    String[] getTestFiles();

    void initialize( EngineBuilder builder );

    void setUp()
        throws Exception;

    void tearDown()
        throws Exception;
}
