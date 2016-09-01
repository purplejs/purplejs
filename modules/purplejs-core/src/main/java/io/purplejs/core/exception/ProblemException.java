package io.purplejs.core.exception;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourcePath;

/**
 * This exception indicates a problem with one of the resources.
 */
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

    /**
     * Returns the resource path where the problem occurred.
     *
     * @return path for the problematic resource.
     */
    public ResourcePath getPath()
    {
        return this.path;
    }

    /**
     * Returns the line-number where the problem occurred.
     *
     * @return line number where the problem occurred.
     */
    public int getLineNumber()
    {
        return this.lineNumber;
    }

    /**
     * Stack of method calls where the problem occurred.
     *
     * @return stack of method calls.
     */
    public List<String> getCallStack()
    {
        return this.callStack;
    }

    /**
     * Returns the inner problem exception if nested.
     *
     * @return inner problem exception if nested.
     */
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

    /**
     * This class helps build a resource problem exception.
     */
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

        /**
         * Sets the cause of the exception (if any).
         *
         * @param cause Cause for this exception.
         * @return instance of this builder.
         */
        public Builder cause( final Throwable cause )
        {
            this.cause = cause;
            return this;
        }

        /**
         * Sets the message.
         *
         * @param message Message to show.
         * @param args    Optional arguments.
         * @return instance of this builder.
         */
        public Builder message( final String message, final Object... args )
        {
            this.message = args.length > 0 ? String.format( message, args ) : message;
            return this;
        }

        /**
         * Sets the resource path.
         *
         * @param path Resource path.
         * @return instance of this builder.
         */
        public Builder path( final ResourcePath path )
        {
            this.path = path;
            return this;
        }

        /**
         * Sets the line-number.
         *
         * @param lineNumber Line number.
         * @return instance of this builder.
         */
        public Builder lineNumber( final int lineNumber )
        {
            this.lineNumber = lineNumber;
            return this;
        }

        /**
         * Add a call-line to the call-stack.
         *
         * @param name       Name of function.
         * @param lineNumber Line number of call-line.
         * @return instance of this builder.
         */
        public Builder callLine( final String name, final int lineNumber )
        {
            this.callStack.add( String.format( "%s at line %s", name, lineNumber ) );
            return this;
        }

        /**
         * Builds the exception.
         *
         * @return a new instance of the exception.
         */
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

    /**
     * Create a new builder for this exception type.
     *
     * @return a new builder for this exception type.
     */
    public static Builder newBuilder()
    {
        return new Builder();
    }
}
