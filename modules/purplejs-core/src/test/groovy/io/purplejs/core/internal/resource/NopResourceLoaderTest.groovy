package io.purplejs.core.internal.resource

import io.purplejs.core.exception.NotFoundException
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class NopResourceLoaderTest
    extends Specification
{
    private NopResourceLoader loader;

    def setup()
    {
        this.loader = new NopResourceLoader();
    }

    def "load"()
    {
        when:
        this.loader.load( ResourcePath.from( "/a.txt" ) );

        then:
        thrown NotFoundException;
    }
}
