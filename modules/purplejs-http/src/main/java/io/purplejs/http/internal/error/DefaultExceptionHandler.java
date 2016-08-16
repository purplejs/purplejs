package io.purplejs.http.internal.error;

import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;
import com.google.gson.JsonObject;

import io.purplejs.core.exception.ProblemException;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.error.ExceptionHandler;
import io.purplejs.http.error.ExceptionInfo;
import io.purplejs.http.internal.response.ResponseBuilderImpl;

public final class DefaultExceptionHandler
    implements ExceptionHandler
{
    @Override
    public Response handle( final ExceptionInfo ex )
    {
        final MediaType type = findRenderType( ex.getRequest() );
        final ResponseBuilderImpl builder = ResponseBuilderImpl.newBuilder();
        builder.status( ex.getStatus() );
        builder.contentType( type );

        final String body = renderBody( ex, type );
        builder.body( ByteSource.wrap( body.getBytes( Charsets.UTF_8 ) ) );

        return builder.build();
    }

    private String renderBody( final ExceptionInfo ex, final MediaType type )
    {
        if ( isHtmlType( type ) )
        {
            return renderHtml( ex );
        }

        return renderJson( ex );
    }

    private String renderHtml( final ExceptionInfo ex )
    {
        final ErrorPageBuilder builder = new ErrorPageBuilder();
        builder.cause( ex.getCause() );
        builder.status( ex.getStatus().getCode() );
        builder.title( ex.getStatus().getReasonPhrase() );
        builder.description( getDescription( ex.getCause() ) );
        builder.lines( ex.getLines() );
        builder.problem( findProblemException( ex.getCause() ) );

        return builder.build();
    }

    private String renderJson( final ExceptionInfo ex )
    {
        final JsonObject json = new JsonObject();
        json.addProperty( "status", ex.getStatus().getCode() );
        json.addProperty( "message", ex.getStatus().getReasonPhrase() );
        json.addProperty( "description", getDescription( ex.getCause() ) );
        return json.toString();
    }

    private MediaType findRenderType( final Request request )
    {
        return findRenderType( request.getHeaders().getAccept() );
    }

    private MediaType findRenderType( final List<MediaType> accept )
    {
        for ( final MediaType type : accept )
        {
            if ( isHtmlType( type ) )
            {
                return MediaType.HTML_UTF_8;
            }

            if ( isJsonType( type ) )
            {
                return MediaType.JSON_UTF_8;
            }
        }

        return MediaType.JSON_UTF_8;
    }

    private boolean isHtmlType( final MediaType type )
    {
        return type.subtype().equalsIgnoreCase( "html" );
    }

    private boolean isJsonType( final MediaType type )
    {
        return type.subtype().equalsIgnoreCase( "json" );
    }

    private String getDescription( final Throwable cause )
    {
        if ( cause == null )
        {
            return "No description";
        }

        return cause.getMessage();
    }

    private ProblemException findProblemException( final Throwable cause )
    {
        if ( cause == null )
        {
            return null;
        }

        if ( cause instanceof ProblemException )
        {
            return (ProblemException) cause;
        }

        return findProblemException( cause.getCause() );
    }
}
