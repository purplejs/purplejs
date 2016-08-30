package io.purplejs.testing;

import org.junit.runner.RunWith;

import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineBuilder;

@RunWith(ScriptRunner.class)
public abstract class ScriptTestSupport
    implements ScriptTestSuite
{
    private String[] testFiles;

    @SuppressWarnings("all")
    public ScriptTestSupport()
    {
        setTestFiles( "**/*-test.js" );
    }

    @Override
    public final String[] getTestFiles()
    {
        return this.testFiles;
    }

    public final void setTestFiles( final String... files )
    {
        this.testFiles = files;
    }

    @Override
    public void initialize( final EngineBuilder builder )
    {
        builder.classLoader( getClass().getClassLoader() );
        builder.module( this );
    }

    @Override
    public void setUp()
        throws Exception
    {
        // Do nothing
    }

    @Override
    public void tearDown()
        throws Exception
    {
        // Do nothing
    }

    @Override
    public void configure( final EngineBinder binder )
    {
        // Do nothing
    }
}
