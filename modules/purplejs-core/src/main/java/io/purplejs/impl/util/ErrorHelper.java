package io.purplejs.impl.util;

import javax.script.ScriptException;

import io.purplejs.resource.ResourcePath;
import io.purplejs.resource.ResourceProblemException;
import jdk.nashorn.api.scripting.NashornException;

public final class ErrorHelper
{
    public final static ErrorHelper INSTANCE = new ErrorHelper();

    public RuntimeException handleError( final Exception e )
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

        return new RuntimeException( e );
    }

    private ResourceProblemException doHandleException( final ScriptException e )
    {
        final ResourceProblemException.Builder builder = ResourceProblemException.newBuilder();
        final Throwable cause = e.getCause();

        builder.cause( cause != null ? cause : e );
        builder.lineNumber( e.getLineNumber() );
        builder.resource( toResourcePath( e.getFileName() ) );
        return builder.build();
    }

    private RuntimeException doHandleException( final RuntimeException e )
    {
        final StackTraceElement elem = findScriptTraceElement( e );
        if ( elem == null )
        {
            return e;
        }

        final ResourceProblemException.Builder builder = ResourceProblemException.newBuilder();
        builder.cause( e );
        builder.lineNumber( elem.getLineNumber() );
        builder.resource( toResourcePath( elem.getFileName() ) );
        return builder.build();
    }

    private ResourcePath toResourcePath( final String name )
    {
        return ResourcePath.from( name );
    }

    private StackTraceElement findScriptTraceElement( final RuntimeException e )
    {
        final StackTraceElement[] elements = NashornException.getScriptFrames( e );
        return elements.length > 0 ? elements[0] : null;
    }
}
