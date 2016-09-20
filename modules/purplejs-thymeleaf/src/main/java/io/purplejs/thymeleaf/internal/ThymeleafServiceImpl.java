package io.purplejs.thymeleaf.internal;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.exceptions.TemplateProcessingException;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.purplejs.core.Engine;
import io.purplejs.core.exception.ProblemException;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptValue;
import io.purplejs.thymeleaf.ThymeleafService;

final class ThymeleafServiceImpl
    implements ThymeleafService
{
    private final TemplateEngine engine;

    ThymeleafServiceImpl()
    {
        this.engine = new TemplateEngine();
        this.engine.setDialects( loadDialects() );
    }

    void initialize( final Engine engine )
    {
        final TemplateResolverImpl resolver = new TemplateResolverImpl( engine.getResourceLoader() );
        this.engine.setTemplateResolver( resolver );
    }

    @Override
    public String render( final ResourcePath view, final ScriptValue model )
    {
        try
        {
            return doRender( view, toMap( model ) );
        }
        catch ( final RuntimeException e )
        {
            throw handleError( e );
        }
    }

    private String doRender( final ResourcePath view, final Map<String, Object> model )
    {
        final Context context = new Context();
        context.setVariables( model );
        return this.engine.process( view.toString(), context );
    }

    static RuntimeException handleError( final RuntimeException e )
    {
        if ( e instanceof TemplateProcessingException )
        {
            return handleError( (TemplateProcessingException) e );
        }

        return e;
    }

    private static RuntimeException handleError( final TemplateProcessingException e )
    {
        final int lineNumber = e.getLine() != null ? e.getLine() : 0;
        final ResourcePath path = e.getTemplateName() != null ? ResourcePath.from( e.getTemplateName() ) : null;

        return ProblemException.newBuilder().
            lineNumber( lineNumber ).
            path( path ).
            cause( e ).
            message( e.getMessage() ).
            build();
    }

    private static Set<IDialect> loadDialects()
    {
        return Sets.newHashSet( ServiceLoader.load( IDialect.class ) );
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> toMap( final ScriptValue value )
    {
        if ( value == null )
        {
            return Maps.newHashMap();
        }

        final Object result = value.toJavaObject();
        if ( result instanceof Map )
        {
            return (Map<String, Object>) result;
        }

        return Maps.newHashMap();
    }
}
