package io.purplejs.core.resource;

import java.util.List;

import com.google.common.collect.Lists;

import jdk.nashorn.api.scripting.NashornException;

public final class ResourceHelper
{
    public static List<ResourcePath> getScriptStack()
    {
        final List<ResourcePath> result = Lists.newArrayList();
        for ( final StackTraceElement e : NashornException.getScriptFrames( new RuntimeException() ) )
        {
            final String file = e.getFileName();
            result.add( ResourcePath.from( file ) );
        }

        return result;
    }

    public static ResourcePath getCurrentScript()
    {
        final List<ResourcePath> stack = getScriptStack();
        return stack.isEmpty() ? null : stack.get( 0 );
    }

    public static ResourcePath getCallingScript()
    {
        final List<ResourcePath> stack = getScriptStack();
        return ( stack.size() > 1 ) ? stack.get( 1 ) : null;
    }
}
