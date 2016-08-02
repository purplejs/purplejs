package io.purplejs.exception;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import io.purplejs.resource.ResourcePath;

public final class ProblemException
    extends RuntimeException
{
    private ResourcePath path;

    private final int lineNumber;

    private final ImmutableList<String> callStack;

    private ProblemException( final Builder builder )
    {
        super( builder.message );
        this.path = builder.path;
        this.lineNumber = builder.lineNumber;
        this.callStack = ImmutableList.copyOf( builder.callStack );

        if ( builder.cause != null )
        {
            initCause( builder.cause );
        }
    }

    public ResourcePath getPath()
    {
        return this.path;
    }

    public int getLineNumber()
    {
        return this.lineNumber;
    }

    public List<String> getCallStack()
    {
        return this.callStack;
    }

    public ProblemException getInnerError()
    {
        return getInnerError( getCause() );
    }

    private ProblemException getInnerError( final Throwable cause )
    {
        if ( cause == null )
        {
            return this;
        }

        if ( cause instanceof ProblemException )
        {
            return (ProblemException) cause;
        }

        return getInnerError( cause.getCause() );
    }

    public static class Builder
    {
        private String message;

        private Throwable cause;

        private ResourcePath path;

        private int lineNumber;

        private final List<String> callStack;

        private Builder()
        {
            this.callStack = Lists.newArrayList();
            this.lineNumber = -1;
        }

        public Builder cause( final Throwable cause )
        {
            this.cause = cause;
            return this;
        }

        public Builder message( final String message, final Object... args )
        {
            this.message = args.length > 0 ? String.format( message, args ) : message;
            return this;
        }

        public Builder path( final ResourcePath path )
        {
            this.path = path;
            return this;
        }

        public Builder lineNumber( final int lineNumber )
        {
            this.lineNumber = lineNumber;
            return this;
        }

        public Builder callLine( final String name, final int lineNumber )
        {
            this.callStack.add( String.format( "%s at line %s", name, lineNumber ) );
            return this;
        }

        public ProblemException build()
        {
            if ( this.message == null )
            {
                this.message = this.cause != null ? this.cause.getMessage() : null;
            }

            if ( this.message == null )
            {
                this.message = "Empty message in exception";
            }

            return new ProblemException( this );
        }
    }

    public static Builder newBuilder()
    {
        return new Builder();
    }
}
