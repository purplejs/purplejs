package io.purplejs.http.impl.error;

import io.purplejs.http.Status;
import io.purplejs.resource.ResourceLoader;

final class ExceptionInfo
{
    private final Status status;

    private String message;

    private Throwable cause;

    private ResourceLoader resourceLoader;

    private ExceptionInfo( final Status status )
    {
        this.status = status;
    }

    Status getStatus()
    {
        return this.status;
    }

    boolean shouldLogAsError()
    {
        return this.status.isServerError() || this.status == Status.BAD_REQUEST;
    }

    String getReasonPhrase()
    {
        return this.status.getReasonPhrase();
    }

    String getMessage()
    {
        if ( this.message != null )
        {
            return this.message;
        }

        final String str = this.cause != null ? this.cause.getMessage() : null;
        return str != null ? str : getReasonPhrase();
    }

    public ExceptionInfo message( final String message )
    {
        this.message = message;
        return this;
    }

    public Throwable getCause()
    {
        return this.cause;
    }

    public ExceptionInfo cause( final Throwable cause )
    {
        this.cause = cause;
        return this;
    }

    public ExceptionInfo resourceLoader( final ResourceLoader resourceLoader )
    {
        this.resourceLoader = resourceLoader;
        return this;
    }

    /*
    public Response toResponse( final Request req )
    {
        final String accept = req.getHeaders().get( HttpHeaders.ACCEPT ).orElse( "" );
        final boolean isHtml = accept.contains( "text/html" );
        return isHtml ? toHtmlResponse() : toJsonResponse();
    }

    private Response toJsonResponse()
    {
        final ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put( "status", this.status.value() );
        node.put( "message", getDescription() );

        return PortalResponse.create().
            status( this.status ).
            body( node.toString() ).
            contentType( MediaType.create( "application", "json" ) ).
            build();
    }

    private Response toHtmlResponse()
    {
        final ErrorPageBuilder builder = new ErrorPageBuilder().
            cause( this.cause ).
            description( getDescription() ).
            resourceService( this.resourceService ).
            status( this.status.value() ).
            title( getReasonPhrase() );

        final String html = builder.build();
        return PortalResponse.create().
            status( this.status ).
            body( html ).
            contentType( MediaType.create( "text", "html" ) ).
            build();
    }
    */

    private String getDescription()
    {
        String str = getMessage();
        final Throwable cause = this.cause != null ? this.cause.getCause() : null;
        if ( cause != null )
        {
            str += " (" + cause.getClass().getName() + ")";
        }

        return str;
    }

    public static ExceptionInfo create( final Status status )
    {
        return new ExceptionInfo( status );
    }
}
