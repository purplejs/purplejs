package io.purplejs.core.internal.executor

import io.purplejs.core.Engine
import io.purplejs.core.resource.ResourcePath

import java.util.function.Supplier

class ExecutionContextImplTest
    extends AbstractExecutorTest
{
    ExecutionContext context

    @Override
    protected void doConfigure()
    {
        this.context = new ExecutionContextImpl( this.executor )
    }

    def "test getEnvironment"()
    {
        when:
        def env = this.context.environment

        then:
        this.environment == env
    }

    def "test getEngine"()
    {
        setup:
        def engine = Mock( Engine.class )
        this.environment.getInstance( Engine.class ) >> engine

        when:
        def expected = this.context.engine

        then:
        engine == expected
    }

    def "test getRegistry"()
    {
        when:
        def registry = this.context.registry

        then:
        this.environment == registry
    }

    def "test dispose"()
    {
        setup:
        def func = Mock( Runnable.class )
        this.context.disposer( func )

        when:
        this.executor.dispose()

        then:
        1 * func.run();
    }

    def "test registerMock"()
    {
        setup:
        addResource( "/a/b/other.js", "module.exports = {};" )

        when:
        def mock = new Object()
        this.context.registerMock( "/a/b/other.js", mock )
        def result = this.context.require( ResourcePath.from( "/a/b/other.js" ) )

        then:
        result == mock
    }

    def "test newBean"()
    {
        when:
        def bean = this.context.newBean( MyTestBean.class.name )

        then:
        bean != null
    }

    def "test newBean not found"()
    {
        when:
        this.context.newBean( 'foo.bar.NoClass' )

        then:
        thrown ClassNotFoundException
    }

    def "test getInstance"()
    {
        setup:
        def expected = new MyTestBean()
        this.environment.getInstance( MyTestBean.class ) >> expected

        when:
        def result = this.context.getInstance( MyTestBean.class.name )

        then:
        result == expected
    }

    def "test getProvider"()
    {
        setup:
        def expected = Mock( Supplier.class )
        this.environment.getProvider( MyTestBean.class ) >> expected

        when:
        def result = this.context.getProvider( MyTestBean.class.name )

        then:
        result == expected
    }

    def "test getInstanceOrNull"()
    {
        setup:
        def expected = Optional.of( new MyTestBean() )
        this.environment.getInstanceOrNull( MyTestBean.class ) >> expected

        when:
        def result = this.context.getInstanceOrNull( MyTestBean.class.name )

        then:
        result == expected
    }

    def "test toScriptValue"()
    {
        when:
        def result = this.context.toScriptValue( 12 )

        then:
        result != null
        result.isValue()
    }

    def "test toNativeObject"()
    {
        when:
        def result = this.context.toNativeObject( 12 )

        then:
        result != null
        result == 12
    }
}
