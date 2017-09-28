package io.purplejs.core.internal.executor

import io.purplejs.core.Environment
import io.purplejs.core.internal.inject.DefaultBeanInjector
import io.purplejs.core.internal.nashorn.NashornRuntimeFactory
import io.purplejs.core.mock.MockResource
import io.purplejs.core.mock.MockResourceLoader
import io.purplejs.core.resource.ResourceResolverBuilder
import spock.lang.Specification

abstract class AbstractExecutorTest
    extends Specification
{
    def ScriptExecutorImpl executor;

    def MockResourceLoader resources;

    def Environment environment;

    def setup()
    {
        this.environment = Mock( Environment.class );

        this.resources = new MockResourceLoader();
        this.environment.getResourceLoader() >> this.resources;
        this.environment.getClassLoader() >> getClass().getClassLoader();
        this.environment.resourceResolver >> ResourceResolverBuilder.newBuilder().build()
        this.environment.beanInjector >> new DefaultBeanInjector()

        this.executor = new ScriptExecutorImpl();
        this.executor.setNashornRuntime( new NashornRuntimeFactory().newRuntime( getClass().getClassLoader() ) );
        this.executor.setEnvironment( this.environment );
        this.executor.init();

        doConfigure();
    }

    final MockResource addResource( final String path, final String text )
    {
        return this.resources.addResource( path, text );
    }

    protected abstract void doConfigure();
}
