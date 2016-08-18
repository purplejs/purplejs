package io.purplejs.core.internal.lib;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.LineProcessor;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

public final class CoreLibHelper
{
    private ByteSource toByteSource( final Object value )
    {
        if ( value instanceof ByteSource )
        {
            return (ByteSource) value;
        }

        return newStream( value.toString() );
    }

    private CharSource toCharSource( final Object value )
    {
        return toByteSource( value ).asCharSource( Charsets.UTF_8 );
    }

    public ByteSource newStream( final String value )
    {
        return ByteSource.wrap( value.getBytes( Charsets.UTF_8 ) );
    }

    public String readText( final Object stream )
        throws Exception
    {
        return toCharSource( stream ).read();
    }

    public List<String> readLines( final Object stream )
        throws Exception
    {
        return toCharSource( stream ).readLines();
    }

    public long streamSize( final Object stream )
        throws Exception
    {
        return toByteSource( stream ).size();
    }

    public void processLines( final Object stream, final Consumer<String> callback )
        throws Exception
    {
        final CharSource source = toCharSource( stream );
        source.readLines( new LineProcessor<Object>()
        {
            @Override
            public boolean processLine( final String line )
                throws IOException
            {
                callback.accept( line );
                return true;
            }

            @Override
            public Object getResult()
            {
                return null;
            }
        } );
    }

    private ResourcePath toPath( final Object path )
    {
        return path instanceof ResourcePath ? (ResourcePath) path : ResourcePath.from( path.toString() );
    }

    public ByteSource loadResource( final ResourceLoader loader, final Object path )
    {
        final Resource resource = loader.loadOrNull( toPath( path ) );
        return resource != null ? resource.getBytes() : null;
    }
}
