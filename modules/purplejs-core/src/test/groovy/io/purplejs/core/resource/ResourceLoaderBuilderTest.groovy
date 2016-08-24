package io.purplejs.core.resource

import io.purplejs.core.internal.resource.ResourceTestSupport

class ResourceLoaderBuilderTest
    extends ResourceTestSupport
{
    def "build"()
    {
        setup:
        def builder = ResourceLoaderBuilder.newBuilder();

        assert builder == builder.add( Mock( ResourceLoader.class ) );
        assert builder == builder.from( getClass().getClassLoader() );
        assert builder == builder.from( getClass().getClassLoader(), "prefix" );
        assert builder == builder.from( this.temporaryFolder.root );

        when:
        def loader = builder.build();

        then:
        loader != null;
    }
}
