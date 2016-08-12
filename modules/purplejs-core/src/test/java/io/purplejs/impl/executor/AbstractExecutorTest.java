package io.purplejs.impl.executor;

import org.junit.Before;
import org.mockito.Mockito;

import io.purplejs.Environment;
import io.purplejs.impl.nashorn.NashornRuntimeFactory;
import io.purplejs.testing.MockResource;
import io.purplejs.testing.MockResourceLoader;

public abstract class AbstractExecutorTest
{
    ScriptExecutorImpl executor;

    private MockResourceLoader resources;

    Environment environment;

    @Before
    public final void setUp()
    {
        this.environment = Mockito.mock( Environment.class );

        this.resources = new MockResourceLoader();
        Mockito.when( this.environment.getResourceLoader() ).thenReturn( this.resources );
        Mockito.when( this.environment.getClassLoader() ).thenReturn( getClass().getClassLoader() );

        this.executor = new ScriptExecutorImpl();
        this.executor.setNashornRuntime( new NashornRuntimeFactory().newRuntime( getClass().getClassLoader() ) );
        this.executor.setEnvironment( this.environment );

        doConfigure();
        this.executor.init();
    }

    final MockResource addResource( final String path, final String text )
    {
        return this.resources.addResource( path, text );
    }

    protected abstract void doConfigure();
}
