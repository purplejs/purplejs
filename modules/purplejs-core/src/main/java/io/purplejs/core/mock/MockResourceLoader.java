package io.purplejs.core.mock;

import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

public final class MockResourceLoader
    implements ResourceLoader
{
    private final Map<ResourcePath, Resource> map;

    public MockResourceLoader()
    {
        this.map = Maps.newHashMap();
    }

    @Override
    public Resource loadOrNull( final ResourcePath path )
    {
        return this.map.get( path );
    }

    public MockResource addResource( final String path, final String text )
    {
        return addResource( ResourcePath.from( path ), text );
    }

    public MockResource addResource( final String path, final byte[] bytes )
    {
        return addResource( ResourcePath.from( path ), bytes );
    }

    public MockResource addResource( final ResourcePath path, final String text )
    {
        return addResource( path, text.getBytes( Charsets.UTF_8 ) );
    }

    public MockResource addResource( final ResourcePath path, final byte[] bytes )
    {
        final MockResource resource = new MockResource( path, bytes );
        this.map.put( path, resource );
        return resource;
    }
}
