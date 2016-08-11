package io.purplejs.impl.executor;

import java.util.Map;

import org.junit.Before;
import org.mockito.Mockito;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.ByteSource;

import io.purplejs.Environment;
import io.purplejs.impl.nashorn.NashornRuntimeFactory;
import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;

public abstract class AbstractExecutorTest
{
    ScriptExecutorImpl executor;

    private Map<ResourcePath, Resource> resources;

    Environment environment;

    @Before
    public final void setUp()
    {
        this.environment = Mockito.mock( Environment.class );

        this.resources = Maps.newHashMap();
        final ResourceLoader loader = path -> resources.get( path );
        Mockito.when( this.environment.getResourceLoader() ).thenReturn( loader );
        Mockito.when( this.environment.getClassLoader() ).thenReturn( getClass().getClassLoader() );

        this.executor = new ScriptExecutorImpl();
        this.executor.setNashornRuntime( new NashornRuntimeFactory().newRuntime( getClass().getClassLoader() ) );
        this.executor.setEnvironment( this.environment );

        doConfigure();
        this.executor.init();
    }

    final Resource addResource( final ResourcePath path, final String value )
    {
        final Resource resource = Mockito.mock( Resource.class );
        Mockito.when( resource.getBytes() ).thenReturn( ByteSource.wrap( value.getBytes( Charsets.UTF_8 ) ) );
        Mockito.when( resource.getLastModified() ).thenReturn( 0L );
        Mockito.when( resource.getPath() ).thenReturn( path );
        Mockito.when( resource.getSize() ).thenReturn( (long) value.length() );

        this.resources.put( path, resource );
        return resource;
    }

    protected abstract void doConfigure();
}
