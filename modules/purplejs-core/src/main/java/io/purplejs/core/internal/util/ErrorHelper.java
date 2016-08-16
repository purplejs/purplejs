package io.purplejs.core.internal.util;

import javax.script.ScriptException;

import io.purplejs.core.RunMode;
import io.purplejs.core.exception.ProblemException;
import io.purplejs.core.resource.ResourcePath;
import jdk.nashorn.api.scripting.NashornException;

public final class ErrorHelper
{
    public final static ErrorHelper INSTANCE = new ErrorHelper();

    public RuntimeException handleError( final Exception e )
    {
        if ( e instanceof ProblemException )
        {
            return (ProblemException) e;
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

    private ProblemException doHandleException( final ScriptException e )
    {
        final ProblemException.Builder builder = ProblemException.newBuilder();
        final Throwable cause = e.getCause();

        builder.cause( cause != null ? cause : e );
        builder.lineNumber( e.getLineNumber() );
        builder.path( toResourcePath( e.getFileName() ) );
        return builder.build();
    }

    private RuntimeException doHandleException( final RuntimeException e )
    {
        final StackTraceElement elem = findScriptTraceElement( e );
        if ( elem == null )
        {
            return e;
        }

        if ( RunMode.isTestMode() )
        {
            rewriteScriptReferences( e );
        }

        final ProblemException.Builder builder = ProblemException.newBuilder();
        builder.cause( e );
        builder.lineNumber( elem.getLineNumber() );
        builder.path( toResourcePath( elem.getFileName() ) );
        return builder.build();
    }

    private ResourcePath toResourcePath( final String name )
    {
        return ResourcePath.from( name );
    }

    private static StackTraceElement findScriptTraceElement( final RuntimeException e )
    {
        final StackTraceElement[] elements = NashornException.getScriptFrames( e );
        return elements.length > 0 ? elements[0] : null;
    }

    private void rewriteScriptReferences( final Throwable e )
    {
        final StackTraceElement[] list = e.getStackTrace();
        for ( int i = 0; i < list.length; i++ )
        {
            list[i] = rewriteScriptReference( list[i] );
        }

        e.setStackTrace( list );
    }

    private StackTraceElement rewriteScriptReference( final StackTraceElement elem )
    {
        final String fileName = elem.getFileName();
        if ( ( fileName != null ) && !fileName.startsWith( "/" ) )
        {
            return elem;
        }

        final String name = ResourcePath.from( fileName ).getName();
        return new StackTraceElement( elem.getClassName(), elem.getMethodName(), name, elem.getLineNumber() );
    }
}
