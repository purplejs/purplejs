package io.purplejs.core.internal.util;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourcePath;

// TODO: Move to more permanent location.
public class ResourceHelper
{
    private static List<ResourcePath> getScriptStack()
    {
        final List<ResourcePath> result = Lists.newArrayList();
        for ( final StackTraceElement e : Thread.currentThread().getStackTrace() )
        {
            final String file = e.getFileName();
            if ( ( file != null ) && file.endsWith( ".js" ) )
            {
                result.add( ResourcePath.from( file ) );
            }
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
