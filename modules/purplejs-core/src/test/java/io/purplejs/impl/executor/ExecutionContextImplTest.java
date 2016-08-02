package io.purplejs.impl.executor;

import java.util.Optional;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.purplejs.Engine;
import io.purplejs.Environment;
import io.purplejs.context.ExecutionContext;
import io.purplejs.exception.NotFoundException;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class ExecutionContextImplTest
{
    private ExecutionContext context;

    private ScriptExecutor executor;

    private Engine engine;

    private Environment environment;

    private ResourcePath resource;

    private ResourceLoader resourceLoader;

    @Before
    public void setUp()
    {
        this.resourceLoader = Mockito.mock( ResourceLoader.class );

        this.engine = Mockito.mock( Engine.class );
        this.environment = Mockito.mock( Environment.class );
        Mockito.when( this.environment.getInstance( Engine.class ) ).thenReturn( this.engine );

        Mockito.when( this.environment.getResourceLoader() ).thenReturn( this.resourceLoader );
        Mockito.when( this.environment.getClassLoader() ).thenReturn( getClass().getClassLoader() );

        this.executor = Mockito.mock( ScriptExecutor.class );
        Mockito.when( this.executor.getEnvironment() ).thenReturn( this.environment );

        this.resource = ResourcePath.from( "/a/b/test.js" );
        this.context = new ExecutionContextImpl( this.executor, this.resource );
    }

    @Test
    public void getResource()
    {
        assertSame( this.resource, this.context.getResource() );
    }

    @Test
    public void getEnvironment()
    {
        assertSame( this.environment, this.context.getEnvironment() );
    }

    @Test
    public void getEngine()
    {
        assertSame( this.engine, this.context.getEngine() );
    }

    @Test
    public void getRegistry()
    {
        assertSame( this.environment, this.context.getRegistry() );
    }

    @Test
    public void require()
    {
        Mockito.when( this.resourceLoader.exists( ResourcePath.from( "/a/b/other.js" ) ) ).thenReturn( true );

        final Object expected = new Object();
        Mockito.when( this.executor.executeRequire( ResourcePath.from( "/a/b/other.js" ) ) ).thenReturn( expected );

        final Object result = this.context.require( "/a/b/other.js" );
        assertSame( expected, result );
    }

    @Test(expected = NotFoundException.class)
    public void require_not_found()
    {
        this.context.require( "/a/b/other.js" );
    }

    @Test
    public void resolve()
    {
        final ResourcePath result = this.context.resolve( "/a/b/other.js" );
        assertEquals( ResourcePath.from( "/a/b/other.js" ), result );
    }

    @Test
    public void disposer()
    {
        final Runnable func = () -> {
        };

        this.context.disposer( func );
        Mockito.verify( this.executor, Mockito.times( 1 ) ).registerDisposer( this.resource, func );
    }

    @Test
    public void registerMock()
    {
        final Object mock = new Object();
        this.context.registerMock( "/a/b/other.js", mock );

        Mockito.verify( this.executor, Mockito.times( 1 ) ).registerMock( ResourcePath.from( "/a/b/other.js" ), mock );
    }

    @Test
    public void newBean()
        throws Exception
    {
        final MyTestBean bean1 = this.context.newBean( MyTestBean.class );
        assertNotNull( bean1 );

        final Object bean2 = this.context.newBean( MyTestBean.class.getName() );
        assertNotNull( bean2 );
    }

    @Test(expected = ClassNotFoundException.class)
    public void newBean_notFound()
        throws Exception
    {
        this.context.newBean( "foo.bar.NoClass" );
    }

    @Test
    public void getInstance()
        throws Exception
    {
        final MyTestBean expected = new MyTestBean();
        Mockito.when( this.environment.getInstance( MyTestBean.class ) ).thenReturn( expected );

        final Object bean = this.context.getInstance( MyTestBean.class.getName() );
        assertSame( expected, bean );
    }

    @Test
    public void getProvider()
        throws Exception
    {
        final Supplier<MyTestBean> expected = MyTestBean::new;
        Mockito.when( this.environment.getProvider( MyTestBean.class ) ).thenReturn( expected );

        final Supplier<?> supplier = this.context.getProvider( MyTestBean.class.getName() );
        assertSame( expected, supplier );
    }

    @Test
    public void getOptional()
        throws Exception
    {
        final Optional<MyTestBean> expected = Optional.of( new MyTestBean() );
        Mockito.when( this.environment.getOptional( MyTestBean.class ) ).thenReturn( expected );

        final Optional<?> optional = this.context.getOptional( MyTestBean.class.getName() );
        assertSame( expected, optional );
    }

    @Test
    public void toScriptValue()
    {
        // TODO: Test toScriptValue
    }

    @Test
    public void toNativeObject()
    {
        // TODO: Test toNativeObject
    }
}
