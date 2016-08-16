package io.purplejs.core.internal.executor;

import java.util.Optional;
import java.util.function.Supplier;

import org.junit.Test;
import org.mockito.Mockito;

import io.purplejs.core.Engine;
import io.purplejs.core.context.ExecutionContext;
import io.purplejs.core.exception.NotFoundException;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptValue;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import static org.junit.Assert.*;

public class ExecutionContextImplTest
    extends AbstractExecutorTest
{
    private ExecutionContext context;

    private ResourcePath resource;

    @Override
    protected void doConfigure()
    {
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
        final Engine engine = Mockito.mock( Engine.class );
        Mockito.when( this.environment.getInstance( Engine.class ) ).thenReturn( engine );

        assertSame( engine, this.context.getEngine() );
    }

    @Test
    public void getRegistry()
    {
        assertSame( this.environment, this.context.getRegistry() );
    }

    @Test
    public void require()
    {
        addResource( "/a/b/other.js", "module.exports = {};" );

        final Object result = this.context.require( "/a/b/other.js" );
        assertNotNull( result );
        assertTrue( result instanceof ScriptObjectMirror );
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
        final Runnable func = Mockito.mock( Runnable.class );

        this.context.disposer( func );
        this.executor.dispose();

        Mockito.verify( func, Mockito.times( 1 ) ).run();
    }

    @Test
    public void registerMock()
    {
        addResource( "/a/b/other.js", "module.exports = {};" );

        final Object mock = new Object();
        this.context.registerMock( "/a/b/other.js", mock );

        final Object result = this.context.require( "/a/b/other.js" );
        assertSame( mock, result );
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
        final ScriptValue value = this.context.toScriptValue( 12 );
        assertNotNull( value );
        assertTrue( value.isValue() );
    }

    @Test
    public void toNativeObject()
    {
        final Object value = this.context.toNativeObject( 12 );
        assertNotNull( value );
        assertEquals( 12, value );
    }
}
