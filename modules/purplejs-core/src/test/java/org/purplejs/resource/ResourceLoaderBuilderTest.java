package org.purplejs.resource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ResourceLoaderBuilderTest
{
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testBuild()
    {
        final ResourceLoaderBuilder builder = ResourceLoaderBuilder.create();

        assertSame( builder, builder.add( Mockito.mock( ResourceLoader.class ) ) );
        assertSame( builder, builder.from( getClass().getClassLoader() ) );
        assertSame( builder, builder.from( getClass().getClassLoader(), "prefix" ) );
        assertSame( builder, builder.from( getClass().getClassLoader(), "prefix" ) );
        assertSame( builder, builder.from( this.temporaryFolder.getRoot() ) );

        final ResourceLoader loader = builder.build();
        assertNotNull( loader );
    }
}
