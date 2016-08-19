package io.purplejs.core.resource

import io.purplejs.core.exception.NotFoundException
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
