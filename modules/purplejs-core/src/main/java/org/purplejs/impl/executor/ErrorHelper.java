package org.purplejs.impl.executor;

import javax.script.ScriptException;

import org.purplejs.resource.ResourcePath;
import org.purplejs.resource.ResourceProblemException;

import com.google.common.base.Throwables;

import jdk.nashorn.api.scripting.NashornException;

final class ErrorHelper
{
    public static RuntimeException handleError( final Exception e )
    {
        if ( e instanceof ResourceProblemException )
        {
            return (ResourceProblemException) e;
        }

        if ( e instanceof ScriptException )
        {
            return doHandleException( (ScriptException) e );
        }

        if ( e instanceof RuntimeException )
        {
            return doHandleException( (RuntimeException) e );
        }

        return Throwables.propagate( e );
    }

    private static ResourceProblemException doHandleException( final ScriptException e )
    {
        final ResourceProblemException.Builder builder = ResourceProblemException.create();
        builder.cause( e.getCause() );
        builder.lineNumber( e.getLineNumber() );
        builder.resource( toResourcePath( e.getFileName() ) );
        return builder.build();
    }

    private static RuntimeException doHandleException( final RuntimeException e )
    {
        final StackTraceElement elem = findScriptTraceElement( e );
        if ( elem == null )
        {
            return e;
        }

        final ResourceProblemException.Builder builder = ResourceProblemException.create();
        builder.cause( e );
        builder.lineNumber( elem.getLineNumber() );
        builder.resource( toResourcePath( elem.getFileName() ) );
        return builder.build();
    }

    private static ResourcePath toResourcePath( final String name )
    {
        try
        {
            return ResourcePath.from( name );
        }
        catch ( final IllegalArgumentException e )
        {
            return null;
        }
    }

    private static StackTraceElement findScriptTraceElement( final RuntimeException e )
    {
        final StackTraceElement[] elements = NashornException.getScriptFrames( e );
        return elements.length > 0 ? elements[0] : null;
    }
}
