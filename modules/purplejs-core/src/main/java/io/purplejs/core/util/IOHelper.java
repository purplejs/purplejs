package io.purplejs.core.util;

import java.io.InputStream;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSource;

import io.purplejs.core.exception.ExceptionHelper;

public final class IOHelper
{
    public static CharSource toCharSource( final ByteSource source )
    {
        return source.asCharSource( Charsets.UTF_8 );
    }

    public static String readString( final CharSource source )
    {
        return ExceptionHelper.wrap( source::read );
    }

    public static String readString( final ByteSource source )
    {
        return readString( toCharSource( source ) );
    }

    public static ByteSource toByteSource( final InputStream in )
    {
        return ExceptionHelper.wrap( () -> ByteSource.wrap( ByteStreams.toByteArray( in ) ) );
    }

    public static List<String> readLines( final ByteSource source )
    {
        return readLines( toCharSource( source ) );
    }

    public static List<String> readLines( final CharSource source )
    {
        return ExceptionHelper.wrap( source::readLines );
    }
}
