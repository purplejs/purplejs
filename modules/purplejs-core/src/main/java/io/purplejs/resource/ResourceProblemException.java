package io.purplejs.resource;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public final class ResourceProblemException
    extends ResourceException
{
    private final String message;

    private final int lineNumber;

    private final ImmutableList<String> callStack;

    private ResourceProblemException( final Builder builder )
    {
        super( builder.resource );
        this.message = builder.message;
        this.lineNumber = builder.lineNumber;
        this.callStack = ImmutableList.copyOf( builder.callStack );

        if ( builder.cause != null )
        {
            initCause( builder.cause );
        }
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }

    public int getLineNumber()
    {
        return this.lineNumber;
    }

    public List<String> getCallStack()
    {
        return this.callStack;
    }

    public ResourceProblemException getInnerError()
    {
        return getInnerError( getCause() );
    }

    private ResourceProblemException getInnerError( final Throwable cause )
    {
        if ( cause == null )
        {
            return this;
        }

        if ( cause instanceof ResourceProblemException )
        {
            return (ResourceProblemException) cause;
        }

        return getInnerError( cause.getCause() );
    }

    public static class Builder
    {
        private String message;

        private Throwable cause;

        private ResourcePath resource;

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

        public Builder resource( final ResourcePath resource )
        {
            this.resource = resource;
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

        public ResourceProblemException build()
        {
            if ( this.message == null )
            {
                this.message = this.cause != null ? this.cause.getMessage() : null;
            }

            if ( this.message == null )
            {
                this.message = "Empty message in exception";
            }

            return new ResourceProblemException( this );
        }
    }

    public static Builder create()
    {
        return new Builder();
    }
}
