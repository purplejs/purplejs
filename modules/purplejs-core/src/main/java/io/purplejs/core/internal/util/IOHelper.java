package io.purplejs.core.internal.util;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
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
}
