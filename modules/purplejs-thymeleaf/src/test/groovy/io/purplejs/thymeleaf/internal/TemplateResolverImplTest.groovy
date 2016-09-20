package io.purplejs.thymeleaf.internal

import io.purplejs.core.RunMode
import io.purplejs.core.mock.MockResourceLoader
import org.thymeleaf.cache.AlwaysValidCacheEntryValidity
import org.thymeleaf.cache.NonCacheableCacheEntryValidity
import spock.lang.Specification

import java.util.concurrent.Callable

class TemplateResolverImplTest
    extends Specification
{
    private MockResourceLoader loader;

    private TemplateResolverImpl resolver;

    def setup()
    {
        this.loader = new MockResourceLoader();
        this.resolver = new TemplateResolverImpl( this.loader );
    }

    def "computeValidity - devMode"()
    {
        when:
        def result = execInMode( RunMode.DEV, {
            return this.resolver.computeValidity( null, null, null, null );
        } );

        then:
        result instanceof NonCacheableCacheEntryValidity;
    }

    def "computeValidity - prodMode"()
    {
        when:
        def result = execInMode( RunMode.PROD, {
            return this.resolver.computeValidity( null, null, null, null );
        } );

        then:
        result instanceof AlwaysValidCacheEntryValidity;
    }

    private static <T> T execInMode( final RunMode mode, final Callable<T> callable )
    {
        def current = RunMode.get();

        try
        {
            mode.set();
            return callable.call();
        }
        finally
        {
            current.set();
        }
    }
}
